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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.OtherClasses.MyMessage;
import ru.egor.OtherClasses.StorageFileNotFoundException;
import ru.egor.entity.Element;
import ru.egor.entity.MyPath;
import ru.egor.entity.Plate;
import ru.egor.service.ElementService;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class ActionController {

    private static final String FILE_PATH = "C:/SaveImagesFromTechnology/Images/";
    private static final String SUFFIX_PATH = ".jpg";
    private final static Logger logger = Logger.getLogger(ActionController.class);

    Gson gson = new Gson();

    @Autowired
    private ElementService elementService;

    @RequestMapping(value = "/")
    public String index(){
        logger.info("Start servlet '/index'");
        return "index";
    }


    @RequestMapping(value = "/elements", method = RequestMethod.GET)
    public String selectAll(Model model){
        List<Element> elements;
        elements = elementService.showAllElements();
        model.addAttribute("elements",elements);
        return "elements";
    }


    @RequestMapping(value = "/addElement", method = RequestMethod.GET)
    public String addElementGet(){
        return "addElement";
    }


    @RequestMapping(value = "/addElement", method = {RequestMethod.POST})
    public String addElementPost(Model model){
        String operation = "add element";
        String errorMessage = "empty fields";
        model.addAttribute("operationName", operation);
        model.addAttribute("errorMessage", errorMessage);
            return "redirect: addElement";
        }

    @RequestMapping(value = "/contacts")
    public String contacts(){
        logger.info("Start servlet '/contacts'");
        return "contacts";
    }

    @RequestMapping(value = "/getElement", method = RequestMethod.GET)
    public String getElement(){
        return "getElement";
    }

    @RequestMapping(value = "/getElement", method = RequestMethod.POST)
    public String getElement(@RequestBody String nameElement){
        String str = "";
        try {
             str = new String (nameElement.getBytes ("UTF-8"), "windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Element> elements = elementService.showElementByName(str);
        return "redirect: getElement";
    }


    @RequestMapping(value = "/plates" , produces = "application/json; charset=UTF-8")
    public String selectAllPlates(Model model){
        logger.info("Start servlet '/plates'");
        List<Plate> plates;
        plates = elementService.showPlates();
        model.addAttribute("plates",plates);
        return "plates";
    }


    @RequestMapping(value = "/addPlates", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addPlates(@RequestBody String data){
        int id;
        logger.info("Start servlet '/addPlates'");
        Plate plate = gson.fromJson(data, Plate.class);
        try {
            id = elementService.addPlate(plate);
        }catch (Exception ex){
            logger.error("Start servlet '/addPlates'");
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }

    @RequestMapping(value = "/downloadtxt",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String downloadtxt() {
        logger.info("Start servlet '/downloadtxt'");
        List<Plate> plates;
        plates = elementService.showPlates();
        return gson.toJson(plates);
    }

    @RequestMapping(value="/uploadFiles")
    public @ResponseBody Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        logger.info("Start servlet '/uploadFiles'");
        return elementService.fileUpload(request, response);
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
        Plate plate = elementService.getPlateById(plateId);
        request.getSession().setAttribute("myPlate", plate);
        return "forward:/plate";
    }

    @RequestMapping(value = "/plate",  method = RequestMethod.GET)
    public String showOnePlate(HttpServletRequest request, Model model){
        logger.info("Start servlet '/plate'");
        Plate plate = (Plate) request.getSession().getAttribute("myPlate");
        List <MyPath> pathes = elementService.getMypathForOneElement(plate.getPlateId());
        model.addAttribute("currentPlate", plate);
        model.addAttribute("countPath", pathes.size());
        logger.info("countPath  " + pathes.size());
        return "plate";
    }


}
