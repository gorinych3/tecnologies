package ru.egor.controllers;


import com.google.gson.Gson;
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

    Gson gson = new Gson();

    @Autowired
    private ElementService elementService;

    @RequestMapping(value = "/")
    public String index(){
        System.out.println("Запуск сервлета index");
        return "index";
    }


    @RequestMapping(value = "/elements", method = RequestMethod.GET)
    public String selectAll(Model model){
        System.out.println("Запуск сервлета selectAll");
        List<Element> elements;
        elements = elementService.showAllElements();
        for (Element element : elements){
            System.out.println(element.getNameElement());
        }
        model.addAttribute("elements",elements);
        return "elements";
    }


        @RequestMapping(value = "/addElement", method = RequestMethod.GET)
    public String addElementGet(){
        System.out.println("Запуск сервлета addElementGet");

        return "addElement";
    }


        @RequestMapping(value = "/addElement", method = {RequestMethod.POST})
    public String addElementPost(Element element ,@RequestBody String string, Model model){
        System.out.println("Запуск сервлета addElementPost Test");
        String operation = "add element";
        String errorMessage = "empty fields";
        model.addAttribute("operationName", operation);
        model.addAttribute("errorMessage", errorMessage);
            return "redirect: addElement";
        }

    @RequestMapping(value = "/contacts")
    public String contacts(){
        System.out.println("Запуск сервлета contacts");
        return "contacts";
    }

    @RequestMapping(value = "/getElement", method = RequestMethod.GET)
    public String getElement(Model model){
        System.out.println("Запуск сервлета getElement");
        return "getElement";
    }

    @RequestMapping(value = "/getElement", method = RequestMethod.POST)
    public String getElement(@RequestBody String nameElement){
        System.out.println("Запуск сервлета getElement");
        String str = "";
        try {
             str = new String (nameElement.getBytes ("UTF-8"), "windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(str);

        List<Element> elements = elementService.showElementByName(str);

        return "redirect: getElement";
    }


    @RequestMapping(value = "/plates" , produces = "application/json; charset=UTF-8")
    public String selectAllPlates(Model model){
        System.out.println("Запуск сервлета selectAllPlates");
        List<Plate> plates;
        plates = elementService.showPlates();
        model.addAttribute("plates",plates);
        return "plates";
    }


    @RequestMapping(value = "/addPlates", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addPlates(@RequestBody String data){
        int id = 0;
        System.out.println("Запуск сервлета addPlates");
        Plate plate = gson.fromJson(data, Plate.class);
        try {
            id = elementService.addPlate(plate);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }

    @RequestMapping(value = "/downloadtxt",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String ajaxTest() {
        System.out.println("Запуск сервлета downloadtxt");
        List<Plate> plates;
        plates = elementService.showPlates();
        return gson.toJson(plates);
    }

    @RequestMapping(value="/uploadFiles")
    public @ResponseBody Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        System.out.println("Запуск сервлета uploadFiles");
        Map<String,Object> map = new HashMap<String,Object>();
        List<String> fileUploadedList = new ArrayList<String>();
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        String fileName = "";
        int plate_id;
        String newFileName = "";
        MyPath path = new MyPath();

        while(itr.hasNext()){
            mpf = request.getFile(itr.next());
            try{
                fileName = mpf.getOriginalFilename();
                plate_id = Integer.parseInt(fileName.substring(fileName.lastIndexOf('-')+1,fileName.lastIndexOf('.')));
                newFileName = FILE_PATH+fileName.replace(" ", "-");
//				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(context.getRealPath("/resources")+"/"+mpf.getOriginalFilename().replace(" ", "-")));
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFileName));
                fileUploadedList.add(mpf.getOriginalFilename().replace(" ", "-"));
                path.setPathId(0);
                path.setPathName(newFileName);
                path.setPlateId(plate_id);
                elementService.addPlatePath(path);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        map.put("Status", 200);
        map.put("SucessfulList", fileUploadedList);
        return map;
    }



    // Using ResponseEntity<InputStreamResource>
    @GetMapping("/download1/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile1(@PathVariable String fileName) throws IOException {
        System.out.println("Запуск download");
        File file = new File(FILE_PATH+fileName+SUFFIX_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
                .body(resource);
    }


    @RequestMapping(value = "/getplate/{id}",  method = RequestMethod.GET)
    public String showOnePlate(@PathVariable String id, Model model, HttpServletRequest request){
        System.out.println("Запуск сервлета showOnePlate post");
        int plateId = Integer.parseInt(id);
        Plate plate = elementService.getPlateById(plateId);
        request.getSession().setAttribute("myPlate", plate);
        return "forward:/plate";
    }

    @RequestMapping(value = "/plate",  method = RequestMethod.GET)
    public String showOnePlate(HttpServletRequest request, Model model){
        System.out.println("Запуск сервлета showOnePlate get");
        Plate plate = (Plate) request.getSession().getAttribute("myPlate");
        List <MyPath> pathes = elementService.getMypathForOneElement(plate.getPlateId());
        model.addAttribute("currentPlate", plate);
        model.addAttribute("countPath", pathes.size());
        return "plate";
    }


}
