package ru.egor.controllers;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.otherclasses.MyMessage;
import ru.egor.entity.Machine;
import ru.egor.entity.MyPath;
import ru.egor.service.MachineService;
import ru.egor.service.MyPathService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class MachineController {

    private static final String FILE_PATH_MACHINES = "C:/SaveImagesFromTechnology/Images/Machines/";
    private static final String SUFFIX_PATH = ".jpg";
    private final static Logger logger = Logger.getLogger(MachineController.class);

    private Gson gson;
    private MachineService machineService;
    private MyPathService myPathService;

    @Autowired
    public MachineController(Gson gson, MachineService machineService, MyPathService myPathService) {
        this.gson = gson;
        this.machineService = machineService;
        this.myPathService = myPathService;
    }

    @RequestMapping(value = "/addMachine", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addMachine(@RequestBody String data){
        int id;
        logger.info("Start servlet '/addMachine'");
        Machine machine = gson.fromJson(data, Machine.class);
        try {
            id = machineService.addMachine(machine);
        }catch (Exception ex){
            logger.error("Error servlet '/addMachine'");
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }

    @RequestMapping(value = "/getTxtDataMachines",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String downloadTxt() {
        logger.info("Start servlet '/getTxtDataMachines'");
        List<Machine> machines;
        machines = machineService.showMachine();
        return gson.toJson(machines);
    }

    @RequestMapping(value="/uploadFilesMachine")
    public @ResponseBody
    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        logger.info("Start servlet 'uploadFilesMachine'");
        return machineService.fileUpload(request, response, FILE_PATH_MACHINES);
    }



    // Using ResponseEntity<InputStreamResource>
    @GetMapping("/downloadMachineFiles/{fileName}")
    public ResponseEntity<InputStreamResource> downloadMashineFile(@PathVariable String fileName) throws IOException {
        logger.info("Start servlet '/downloadMachineFiles/{fileName}'");
        File file = new File(FILE_PATH_MACHINES+fileName+SUFFIX_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
                .body(resource);
    }


    @RequestMapping(value = "/getMachine/{id}",  method = RequestMethod.GET)
    public String showOnePlate(@PathVariable String id, HttpServletRequest request){
        logger.info("Start servlet '/getMachine/{id}'");
        int machId = Integer.parseInt(id);
        Machine machine = machineService.getMachineById(machId);
        request.getSession().setAttribute("myMachine", machine);
        return "forward:/machine";
    }

    @RequestMapping(value = "/machine",  method = RequestMethod.GET)
    public String showOnePlate(HttpServletRequest request, Model model){
        logger.info("Start servlet '/machine'");
        Machine machine = (Machine) request.getSession().getAttribute("myMachine");
        List <MyPath> pathes = myPathService.getMypathForOneMachine(machine.getMachId());
        model.addAttribute("currentMachine", machine);
        model.addAttribute("countPath", pathes.size());
        return "machine";
    }

    @RequestMapping(value = "/deleteMachine/{id}")
    public String deleteOneMachine(@PathVariable String id){
        logger.info("Start servlet '/deleteMachine'");
        machineService.deleteMachineById(Integer.parseInt(id));
        return "redirect:/machines";
    }
}
