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
import ru.egor.entity.*;
import ru.egor.otherclasses.MyMessage;
import ru.egor.service.ElementService;
import ru.egor.service.MyPathService;
import javax.servlet.http.HttpServletRequest;
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
    private MyPathService myPathService;

    @Autowired
    public ElementController(Gson gson, ElementService elementService, MyPathService myPathService) {
        this.gson = gson;
        this.elementService = elementService;
        this.myPathService = myPathService;
    }

    @RequestMapping(value = "/addElement", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addOneElement(@RequestBody String data){
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
        return elementService.getElements();
    }

    @RequestMapping(value="/uploadFilesElements")
    public @ResponseBody
    Map<String,Object> elementFileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        logger.info("Start servlet '/uploadFilesElements'");
        return elementService.fileUploadElement(request, response, FILE_PATH_ELEMENT);
    }

    @RequestMapping(value="/changeFilesElements")
    public @ResponseBody
    Map<String,Object> elementFileChange(MultipartHttpServletRequest request, HttpServletResponse response){
        logger.info("Start servlet '/changeFilesElements'");
        return elementService.fileChangeElement(request, response, FILE_PATH_ELEMENT);
    }

    // Using ResponseEntity<InputStreamResource>
    @GetMapping("/downloadElementFilesPhoto/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFileElementsPhoto(@PathVariable String fileName)throws IOException {
        logger.info("Start servlet '/download1/{fileName}'");
        logger.info("fileName = " + fileName);
        File file = new File(FILE_PATH_ELEMENT+fileName+SUFFIX_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
                .body(resource);
    }

    @RequestMapping(value = "/getelement/{id}",  method = RequestMethod.GET)
    public String showOneElement(@PathVariable String id, HttpServletRequest request){
        logger.info("Start servlet '/getelement/{id}'");
        int elId = Integer.parseInt(id);
        Element element = elementService.getElementById(elId);
        request.getSession().setAttribute("element", element);
        request.getSession().setAttribute("elementPlates", element.getPlates());
        request.getSession().setAttribute("elementTools", element.getTools());
        request.getSession().setAttribute("elementMachines", element.getMachines());
        return "forward:/element";
    }

    @RequestMapping(value = "/element",  method = RequestMethod.GET)
    public String showOneElement(HttpServletRequest request, Model model){
        logger.info("Start servlet '//element'");
        Element element = (Element) request.getSession().getAttribute("element");
        List<MyTool> myTools = (List<MyTool>) request.getSession().getAttribute("elementTools");
        Set<Plate> plates = (Set<Plate>) request.getSession().getAttribute("elementPlates");
        Set<Machine> machines = (Set<Machine>) request.getSession().getAttribute("elementMachines");
        List <MyPath> pathes = myPathService.getMypathForOneElement(element.getElId());
        List<MyPath> photo = new ArrayList<>();
        List<MyPath> tech = new ArrayList<>();
        List<MyPath> prog = new ArrayList<>();
        String pathPrefix = "";
        for(MyPath path : pathes){
            pathPrefix = path.getPathName().substring(path.getPathName().lastIndexOf('/')+1,path.getPathName().lastIndexOf('/')+5);
            if(pathPrefix.equals("phot")){
                photo.add(path);
                request.getSession().setAttribute("phot", photo.size());
            }
            if(pathPrefix.equals("tech")){
                tech.add(path);
                request.getSession().setAttribute("tech", tech.size());
            }
        }
        model.addAttribute("currentElement", element);
        model.addAttribute("currentTool", myTools);
        model.addAttribute("currentPlates", plates);
        model.addAttribute("currentMachines", machines);
        model.addAttribute("countPath", pathes.size());
        model.addAttribute("countPathPhoto", photo.size());
        model.addAttribute("countPathTech", tech.size());

        return "element";
    }

    @RequestMapping(value = "/deleteElement/{id}")
    public String deleteOneElement(@PathVariable String id){
        logger.info("Start servlet '/deleteElement/{id}'");
        elementService.deleteElement(Integer.parseInt(id));
        return "redirect:/elements";
    }

    @RequestMapping(value = "/updateElementPage")
    public String updateElementPage(Model model, HttpServletRequest request){
        logger.info("Запуск сервлета /updateElement");
        System.out.println(request.getSession().getAttribute("element").toString());
        model.addAttribute("currentElement", request.getSession().getAttribute("element"));
        model.addAttribute("countPathPhoto", request.getSession().getAttribute("phot"));
        model.addAttribute("countPathTech", request.getSession().getAttribute("tech"));
        return "updateElementPage";
    }

    @RequestMapping(value = "/getTxtDataOneElement/{id}",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String getTxtDataOneElement(@PathVariable String id) {
        logger.info("Start servlet '/getTxtDataOneElement'");
        return gson.toJson(elementService.getElementById(Integer.parseInt(id)));
    }

    @RequestMapping(value = "/deleteFilesElements", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String deleteFile(@RequestBody String data){
        logger.info("Start servlet '/deleteFilesElements'");
        elementService.deleteFile(data, FILE_PATH_ELEMENT);
        return gson.toJson(new MyMessage("success delete"));
    }

    @RequestMapping(value = "/updateElement", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String updateOneElement(@RequestBody String data){
        logger.info("Start servlet '/updateElement'");
        try {
            elementService.updateElement(gson.fromJson(data, Element.class));
        }catch (Exception ex){
            logger.error("Error servlet '/updateElement'");
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success"));
    }
}
