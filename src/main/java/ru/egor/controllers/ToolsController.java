package ru.egor.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
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
import ru.egor.entity.MyTool;
import ru.egor.entity.Plate;
import ru.egor.service.ElementService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Controller
public class ToolsController {

    private static final String FILE_PATH_TOOLS = "C:/SaveImagesFromTechnology/Images/Tools/";
    private static final String SUFFIX_PATH = ".jpg";
    private final static Logger logger = Logger.getLogger(ElementController.class);

    Gson gson = new Gson();

    @Autowired
    private ElementService elementService;

    @RequestMapping(value = "/addTool", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addTool(@RequestBody String data){
        logger.info("Start servlet '/addTool'");
        int id;
        try {
            id = elementService.addTool(data);
        }catch (Exception ex){
            logger.error("Start servlet '/addTool'");
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }

    @RequestMapping(value = "/getTxtDataTools",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String getListMyTools() {
        logger.info("Start servlet '/getTxtDataTools'");
        String listTools = elementService.getMyTools();
        System.out.println(listTools);
        return listTools;
    }

    @RequestMapping(value="/uploadFilesTools")
    public @ResponseBody
    Map<String,Object> toolsFileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        logger.info("Start servlet '/uploadFilesTools'");
        return elementService.fileUpload(request, response, FILE_PATH_TOOLS, "tool");
    }

    // Using ResponseEntity<InputStreamResource>
    @GetMapping("/downloadToolsFiles/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFileTools(@PathVariable String fileName) throws IOException {
        logger.info("Start servlet '/download1/{fileName}'");
        File file = new File(FILE_PATH_TOOLS+fileName+SUFFIX_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
                .body(resource);
    }

}
