package ru.egor.controllers;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.otherclasses.MyMessage;
import ru.egor.entity.MyPath;
import ru.egor.entity.Plate;
import ru.egor.service.MyPathService;
import ru.egor.service.PlateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PlateController {

    private static final String FILE_PATH = "C:/SaveImagesFromTechnology/Images/Plates/";
    private static final String SUFFIX_PATH = ".jpg";
    private final static Logger logger = Logger.getLogger(PlateController.class);

    private final Gson gson;
    private PlateService plateService;
    private MyPathService myPathService;

    @Autowired
    public PlateController(Gson gson, PlateService plateService, MyPathService myPathService) {
        this.gson = gson;
        this.plateService = plateService;
        this.myPathService = myPathService;
    }

    @RequestMapping(value = "/addPlates", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addPlates(@RequestBody String data){
        int id;
        logger.info("Start servlet '/addPlates'");
        Plate plate = gson.fromJson(data, Plate.class);
        try {
            id = plateService.addPlate(plate);
        }catch (Exception ex){
            logger.error("Error servlet '/addPlates'");
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }

    @RequestMapping(value = "/getTxtDataPlate",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String downloadTxt() {
        logger.info("Start servlet '/getTxtDataPlate'");
        List<Plate> plates;
        plates = plateService.showPlates();
        return gson.toJson(plates);
    }

    @RequestMapping(value="/uploadFiles")
    public @ResponseBody
    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        logger.info("Start servlet '/uploadFiles'");
        return plateService.fileUpload(request, response, FILE_PATH);
    }



    // Using ResponseEntity<InputStreamResource>
    @GetMapping("/download1/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile1(@PathVariable String fileName) throws IOException {
        logger.info("Start servlet '/download1/{fileName}'");
        File file = new File(FILE_PATH+fileName+SUFFIX_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
                .body(resource);
    }


    @RequestMapping(value = "/getplate/{id}",  method = RequestMethod.GET)
    public String showOnePlate(@PathVariable String id, HttpServletRequest request){
        logger.info("Start servlet '/getplate/{id}'");
        int plateId = Integer.parseInt(id);
        Plate plate = plateService.getPlateById(plateId);
        request.getSession().setAttribute("myPlate", plate);
        return "forward:/plate";
    }

    @RequestMapping(value = "/plate",  method = RequestMethod.GET)
    public String showOnePlate(HttpServletRequest request, Model model){
        logger.info("Start servlet '/plate'");
        Plate plate = (Plate) request.getSession().getAttribute("myPlate");
        List <MyPath> pathes = myPathService.getMypathForOnePlate(plate.getPlateId());
        model.addAttribute("currentPlate", plate);
        model.addAttribute("countPath", pathes.size());
        return "plate";
    }

    @RequestMapping(value = "/deletePlate/{id}")
     public String deleteOnePlate(@PathVariable String id){
        logger.info("Start servlet '/deletePlate'");
        plateService.deletePlateById(Integer.parseInt(id));
        return "redirect:/plates";
    }
}
