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
import ru.egor.entity.Element;
import ru.egor.otherclasses.MyMessage;
import ru.egor.service.ElementService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.*;

@Controller
@RequestMapping(value = "/")
public class ElementController {

    private static final String FILE_PATH_ELEMENT = "C:/SaveImagesFromTechnology/Elements/";
    private static final String SUFFIX_PATH = ".jpg";

    private final static Logger logger = Logger.getLogger(ElementController.class);

    private Gson gson;
    private ElementService elementService;

    @Autowired
    public ElementController(Gson gson, ElementService elementService) {
        this.gson = gson;
        this.elementService = elementService;
    }

    @RequestMapping(value = "/addElement", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addOneElement(@RequestBody String data){
        logger.info("Start servlet '/addTool'");
        System.out.println(data);
        int id;
        try {
            id = elementService.addElement(data);
        }catch (Exception ex){
            logger.error("Start servlet '/addTool'");
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }

    @RequestMapping(value = "/getTxtDataElements",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String getListElements() {
        logger.info("Start servlet '/getTxtDataElements'");
        String listElements = elementService.getElements();
        System.out.println(listElements);
        return elementService.getElements();
    }

    @RequestMapping(value="/uploadFilesElements")
    public @ResponseBody
    Map<String,Object> elementFileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        logger.info("Start servlet '/uploadFilesTools'");
        return elementService.fileUploadElement(request, response, FILE_PATH_ELEMENT);
    }

    // Using ResponseEntity<InputStreamResource>
    @GetMapping("/downloadElementFilesPhoto/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFileElementsPhoto(@PathVariable String fileName) throws IOException {
        logger.info("Start servlet '/download1/{fileName}'");
        File file = new File(FILE_PATH_ELEMENT+fileName+SUFFIX_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
                .body(resource);
    }

}
