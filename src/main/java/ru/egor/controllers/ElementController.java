package ru.egor.controllers;


import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.egor.entity.Element;
import ru.egor.service.ElementService;

import java.io.*;

import java.util.*;

@Controller
@RequestMapping(value = "/")
public class ElementController {

    private static final String FILE_PATH = "C:/SaveImagesFromTechnology/Images/";
    private static final String SUFFIX_PATH = ".jpg";
    private final static Logger logger = Logger.getLogger(ElementController.class);

    Gson gson = new Gson();

    @Autowired
    private ElementService elementService;


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



}
