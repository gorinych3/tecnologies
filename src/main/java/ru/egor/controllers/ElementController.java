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
import ru.egor.entity.Element;
import ru.egor.otherclasses.MyMessage;
import ru.egor.service.ElementService;

import java.io.*;

import java.util.*;

@Controller
@RequestMapping(value = "/")
public class ElementController {

    private static final String FILE_PATH = "C:/SaveImagesFromTechnology/Docs/";
    private static final String SUFFIX_PATH = ".jpg";
    private static final String SUFFIX_PATH_DOCX = ".docx";
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
    public String addElement(@RequestBody String data){
        logger.info("Start servlet '/addTool'");
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

    // Using ResponseEntity<InputStreamResource>
    @GetMapping("/getWordDocument/{fileName}")
    public ResponseEntity<InputStreamResource> downloadMashineFile(@PathVariable String fileName) throws IOException {
        logger.info("Start servlet '/getWordDocument/{fileName}'");
        File file = new File(FILE_PATH+fileName+SUFFIX_PATH_DOCX);
        if(file.createNewFile()){
            System.out.println("file.txt файл создан в корневой директории проекта");
        }else System.out.println("file.txt файл уже существует в корневой директории проекта");
//        File file = new File(FILE_PATH_MACHINES+fileName+SUFFIX_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
                .body(resource);
    }
}
