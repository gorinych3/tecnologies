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
import ru.egor.OtherClasses.MyMessage;
import ru.egor.entity.Machine;
import ru.egor.entity.MyPath;
import ru.egor.entity.Plate;
import ru.egor.service.ElementService;

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
    private final static Logger logger = Logger.getLogger(ElementController.class);

    Gson gson = new Gson();

    @Autowired
    private ElementService elementService;

//    @RequestMapping(value = "/addMachine", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
//    @ResponseBody
//    public String addMachine(@RequestBody String data){
//        int id;
//        logger.info("Start servlet '/addMachine'");
//        Machine machine = gson.fromJson(data, Machine.class);
//        try {
//            id = elementService.addMachine(machine);
//        }catch (Exception ex){
//            logger.error("Start servlet '/addMachine'");
//            return gson.toJson(new MyMessage(ex.getMessage()));
//        }
//        return gson.toJson(new MyMessage("success", id));
//    }

    @RequestMapping(value = "/getTxtDataMachines",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String downloadTxt() {
        logger.info("Start servlet '/getTxtDataMachines'");
        List<Machine> machines;
        machines = elementService.showMachine();
        return gson.toJson(machines);
    }

//    @RequestMapping(value="/uploadFilesMachine")
//    public @ResponseBody
//    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
//        logger.info("Start servlet 'uploadFilesMachine'");
//        return elementService.fileUpload(request, response, FILE_PATH_MACHINES, "machine");
//    }
//
//

//    // Using ResponseEntity<InputStreamResource>
//    @GetMapping("/download1/{fileName}")
//    public ResponseEntity<InputStreamResource> downloadFile1(@PathVariable String fileName) throws IOException {
//        logger.info("Start servlet '/download1/{fileName}'");
//        File file = new File(FILE_PATH_MACHINES+fileName+SUFFIX_PATH);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment;filename=" + file.getName())
//                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
//                .body(resource);
//    }
//
//
//    @RequestMapping(value = "/getplate/{id}",  method = RequestMethod.GET)
//    public String showOnePlate(@PathVariable String id, HttpServletRequest request){
//        logger.info("Start servlet '/getplate/{id}'");
//        int plateId = Integer.parseInt(id);
//        Plate plate = elementService.getPlateById(plateId);
//        request.getSession().setAttribute("myPlate", plate);
//        return "forward:/plate";
//    }
//
//    @RequestMapping(value = "/plate",  method = RequestMethod.GET)
//    public String showOnePlate(HttpServletRequest request, Model model){
//        logger.info("Start servlet '/plate'");
//        Plate plate = (Plate) request.getSession().getAttribute("myPlate");
//        List <MyPath> pathes = elementService.getMypathForOnePlate(plate.getPlateId());
//        model.addAttribute("currentPlate", plate);
//        model.addAttribute("countPath", pathes.size());
//        return "plate";
//    }
//
//    @RequestMapping(value = "/deletePlate/{id}")
//    public String deleteOnePlate(@PathVariable String id){
//        logger.info("Start servlet '/deletePlate'");
//        elementService.deletePlateById(Integer.parseInt(id));
//        return "redirect:/plates";
//    }
}
