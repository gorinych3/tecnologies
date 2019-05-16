package ru.egor.controllers;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.egor.OtherClasses.MyMessage;
import ru.egor.service.ElementService;

@Controller
public class DrillController {

    private final static Logger logger = Logger.getLogger(ElementController.class);

    Gson gson = new Gson();

    @Autowired
    private ElementService elementService;

    @RequestMapping(value = "/addDrill", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addDrill(@RequestBody String data){
        logger.info("Start servlet '/addDrill'");
        int id;
        try {
            id = elementService.addDrill(data);
        }catch (Exception ex){
            logger.error("Error servlet '/addDrill'");
            System.out.println(ex.getMessage());
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }
}
