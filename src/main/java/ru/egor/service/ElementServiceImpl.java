package ru.egor.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.dao.ElementDAO;
import ru.egor.entity.*;

import java.util.*;

@Service
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional
public class ElementServiceImpl implements ElementService {

    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);

    private Gson gson;
    private ElementDAO elementDAO;
    private MyToolService myToolService;

    @Autowired
    public ElementServiceImpl(Gson gson, ElementDAO elementDAO, MyToolService myToolService) {
        this.gson = gson;
        this.elementDAO = elementDAO;
        this.myToolService = myToolService;
    }


    @Override
    public String getElements(){
        return gson.toJson(elementDAO.getElements());
    }

    @Override
    public List<Element> showElementByName(String nameElement) {
        return elementDAO.showElementByName(nameElement);
    }

    @Override
    public int addElement(String data) {
        logger.info("Start service 'addElement'");
        System.out.println(data);
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        MyTool myTool = new MyTool();
        Element element = new Element();
        element.setNameElement(jsonObject.get("nameElement").getAsString());
        element.setIdNumb(jsonObject.get("idNumb").getAsString());
        element.setTechnology(jsonObject.get("technology").getAsString());
        element.setProgram(jsonObject.get("program").getAsString());
        element.setSetup(jsonObject.get("setup").getAsString());
        element.setNotation(jsonObject.get("notation").getAsString());
        JsonArray toolNames = jsonObject.get("tools").getAsJsonArray();
        List<MyTool> myTools = new ArrayList<>();
        for (int i = 0; i < myTools.size(); i++){
            myTool = myToolService.getToolById(toolNames.get(i).getAsInt());
            myTools.add(myTool);
        }
//        Plate plate = new Plate();
//            JsonArray plateNames = jsonObject.get("platesId").getAsJsonArray();
//            Set<Plate> plates = new HashSet<>();
//            for (int i = 0; i < plateNames.size(); i++){
//                Plate plate = plateService.getPlateById(plateNames.get(i).getAsInt());
//                plates.add(plate);
//            }
//            myTool.setPlates(plates);
//            return myToolDAO.addTool(myTool);
        return 0;
    }

    @Override
    public void updateElement(Element element) {

    }

    @Override
    public void deleteElement(String name) {
    }

}
