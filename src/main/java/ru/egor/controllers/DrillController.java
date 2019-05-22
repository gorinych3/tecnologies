package ru.egor.controllers;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.egor.otherclasses.MyMessage;
import ru.egor.service.MyToolService;

@Controller
public class DrillController {

    private final static Logger logger = Logger.getLogger(ElementController.class);

    private Gson gson;
    private MyToolService myToolService;

    @Autowired
    public DrillController(Gson gson, MyToolService myToolService) {
        this.gson = gson;
        this.myToolService = myToolService;
    }

    @RequestMapping(value = "/addDrill", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String addDrill(@RequestBody String data){
        logger.info("Start servlet '/addDrill'");
        int id;
        try {
            id = myToolService.addDrill(data);
        }catch (Exception ex){
            logger.error("Error servlet '/addDrill'");
            logger.info(ex.getMessage());
            return gson.toJson(new MyMessage(ex.getMessage()));
        }
        return gson.toJson(new MyMessage("success", id));
    }
}
