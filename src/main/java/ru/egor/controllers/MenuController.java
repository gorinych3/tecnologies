package ru.egor.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {

    private final static Logger logger = Logger.getLogger(ElementController.class);

    @RequestMapping(value = "/")
    public String index(){
        logger.info("Start servlet '/index'");
        return "index";
    }

    @RequestMapping(value = "/tools")
    public String selectAllTools(Model model){
        logger.info("Start servlet '/tools'");
        return "tools";
    }

    @RequestMapping(value = "/plates" , produces = "application/json; charset=UTF-8")
    public String selectAllPlates() {
        logger.info("Start servlet '/plates'");
        return "plates";
    }

    @RequestMapping(value = "/contacts")
    public String contacts(){
        logger.info("Start servlet '/contacts'");
        return "contacts";
    }

    @RequestMapping(value = "/drills")
    public String selectAllDriils(){
        logger.info("Start servlet '/drills'");
        return "drills";
    }

    @RequestMapping(value = "/machines")
    public String selectAllMachines(){
        logger.info("Start servlet '/machines'");
        return "machines";
    }
}
