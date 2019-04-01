package ru.egor.controllers;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.Element;
import ru.egor.entity.Plate;
import ru.egor.entity.PredPlate;
import ru.egor.service.ElementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
public class ActionController {

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

//    @RequestMapping(value = "/addElement", method = RequestMethod.GET)
//    public String addElementGet(Model model){
//        System.out.println("Запуск сервлета addElementGet");
//        List<MyTool> tools = elementService.getMyTools();
//        model.addAttribute("tools",tools);
//        return "addElement";
//    }
//
//    @RequestMapping(value = "/addElement", method = {RequestMethod.POST})
//    public String addElementPost(Element element , List<MyTool> tools,/*MyTool tool,/* Plate plate, Machine machine,*/ Model model){
//        System.out.println("Запуск сервлета addElementPost");
//        String operation = "add element";
//        String errorMessage = "empty fields";
//        model.addAttribute("operationName", operation);
//        model.addAttribute("errorMessage", errorMessage);
//        for(int i = 0; i < tools.size(); i++){
//            System.out.println(tools.get(i));
//        }
////        System.out.println(tool.getName());
//        System.out.println(element.toString());
//        if(!element.getNameElement().isEmpty()) {
//            //List<MyTool> tools = new ArrayList<>();
////            tools.add(tool);
////            element.setTools(tools);
//            System.out.println(element.toString());
////            Set<Plate> plates = element.getPlates();
////            plates.add(plate);
////            element.setPlates(plates);
////            Set<Machine> machines = element.getMachines();
////            machines.add(machine);
////            element.setMachines(machines);
////            model.addAttribute("element", element);
//            return "redirect: listElements";
//        }else {
//            System.out.println("trabla");
//            return "errorPage";
//        }
//    }

        @RequestMapping(value = "/addElement", method = RequestMethod.GET)
    public String addElementGet(){
        System.out.println("Запуск сервлета addElementGet");

        return "addElement";
    }

//    @RequestMapping(value = "/addElement", method = RequestMethod.POST)
//    public @ResponseBody String addElementGet(Element element ,@RequestBody String string, Model model) throws Exception {
//        System.out.println("запуск метода POST");
//        System.out.println(element.toString());
//        System.out.println(string);
//
//        return "addElement";
//    }

        @RequestMapping(value = "/addElement", method = {RequestMethod.POST})
    public String addElementPost(Element element ,@RequestBody String string, Model model){
        System.out.println("Запуск сервлета addElementPost Test");
        String operation = "add element";
        String errorMessage = "empty fields";
        model.addAttribute("operationName", operation);
        model.addAttribute("errorMessage", errorMessage);
        System.out.println(element.toString());
            System.out.println(string);


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

    @RequestMapping(value = "/addElements", method = RequestMethod.GET)
    public String addElemenst(Model model){
        System.out.println("Запуск сервлета addElements");
        return "addElements";
    }

    @RequestMapping(value = "/addElements", method = RequestMethod.POST)
    public String addElemenst(@RequestBody Element element){
        System.out.println("Запуск сервлета addElements");
        return "redirect: elements";
    }

    @RequestMapping(value = "/plates" , produces = "application/json; charset=UTF-8")
    public String selectAllPlates(Model model){
        System.out.println("Запуск сервлета selectAllPlates");
        List<Plate> plates;
        plates = elementService.showPlates();
        model.addAttribute("plates",plates);
        return "plates";
    }

    @RequestMapping(value = "/addPlates", method = RequestMethod.GET)
    public String addPlates(Model model){
        System.out.println("Запуск сервлета addPlates");
        return "addPlates";
    }

    @RequestMapping(value = "/addPlates", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addPlates(@RequestBody String data, Model model){
        System.out.println("Запуск сервлета addPlates");
        Plate plate = gson.fromJson(data, Plate.class);
        System.out.println(plate.toString());
        try {
            elementService.addPlate(plate);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            String error = ex.getMessage();
            return gson.toJson(error);
        }
        String message = gson.toJson("success");
        return message;
    }

    @RequestMapping(value = "/ajaxtest",produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String ajaxTest() {
        System.out.println("Запуск сервлета ajaxtest");
        List<Plate> plates;
        plates = elementService.showPlates();
        String js_plates = gson.toJson(plates);
        //System.out.println(js_plates);
        return js_plates;
    }

    @RequestMapping("/errorPage")
    public String errorPage(){
        System.out.println("Запуск сервлета errorPage");
        return "errorPage";
    }

    @RequestMapping(value="/uploadImages", method = RequestMethod.GET)
    public String addFiles(){

        return "uploadImages";
    }

    @RequestMapping(value="/uploadImages", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<String,Object>();
        List<String> fileUploadedList = new ArrayList<String>();
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;

        while(itr.hasNext()){
            mpf = request.getFile(itr.next());
            try{
//				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(context.getRealPath("/resources")+"/"+mpf.getOriginalFilename().replace(" ", "-")));
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("C:/SaveImagesFromTechnology/Images"+"/"+mpf.getOriginalFilename().replace(" ", "-")));
                fileUploadedList.add(mpf.getOriginalFilename().replace(" ", "-"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        map.put("Status", 200);
        map.put("SucessfulList", fileUploadedList);
        return map;
    }


}
