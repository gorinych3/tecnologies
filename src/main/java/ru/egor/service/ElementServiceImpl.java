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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.dao.ElementDAO;
import ru.egor.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional
public class ElementServiceImpl implements ElementService {

    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);

    private Gson gson;
    private ElementDAO elementDAO;
    private MyPathService myPathService;

    @Autowired
    public ElementServiceImpl(Gson gson, ElementDAO elementDAO, MyPathService myPathService) {
        this.gson = gson;
        this.elementDAO = elementDAO;
        this.myPathService = myPathService;
    }


    @Override
    public String getElements(){
        return gson.toJson(elementDAO.getElements());
    }

    @Override
    public List<Element> showElementByName(String nameElement) {
        return elementDAO.showElementByName(nameElement);
    }

//    @Override
//    public int addElement(String data) {
//        logger.info("Start service 'addElement'");
//        System.out.println(data);
//        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
//        MyTool myTool = new MyTool();
//        Element element = new Element();
//        element.setNameElement(jsonObject.get("nameElement").getAsString());
//        element.setIdNumb(jsonObject.get("idNumb").getAsString());
//        element.setTechnology(jsonObject.get("technology").getAsString());
//        element.setProgram(jsonObject.get("program").getAsString());
//        element.setSetup(jsonObject.get("setup").getAsString());
//        element.setNotation(jsonObject.get("notation").getAsString());
//        JsonArray toolNames = jsonObject.get("tools").getAsJsonArray();
//        List<MyTool> myTools = new ArrayList<>();
//        for (int i = 0; i < myTools.size(); i++){
//            myTool = myToolService.getToolById(toolNames.get(i).getAsInt());
//            myTools.add(myTool);
//        }
////        Plate plate = new Plate();
////            JsonArray plateNames = jsonObject.get("platesId").getAsJsonArray();
////            Set<Plate> plates = new HashSet<>();
////            for (int i = 0; i < plateNames.size(); i++){
////                Plate plate = plateService.getPlateById(plateNames.get(i).getAsInt());
////                plates.add(plate);
////            }
////            myTool.setPlates(plates);
////            return myToolDAO.addTool(myTool);
//        return 0;
//    }


    @Override
    public int addElement(String data) {
        logger.info("start service addElement");
        Element element = gson.fromJson(data, Element.class);
        System.out.println(element.toString());
        return elementDAO.addElement(element);
    }

    @Override
    public void updateElement(Element element) {

    }

    @Override
    public void deleteElement(String name) {
    }

    @Override
    public Map<String, Object> fileUploadElement(MultipartHttpServletRequest request, HttpServletResponse response, String filePath) {
        logger.info("Start service 'fileUploadElement'");
        Map<String,Object> map = new HashMap<String,Object>();
        List<String> fileUploadedList = new ArrayList<String>();
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        String fileName = "";
        int id = 0;
        String newFileName = "";

        while(itr.hasNext()){
            MyPath path = new MyPath();
            mpf = request.getFile(itr.next());
            try{
                fileName = mpf.getOriginalFilename();
                id = Integer.parseInt(fileName.substring(fileName.lastIndexOf('-')+1,fileName.lastIndexOf('.')));
                newFileName = filePath+fileName.replace(" ", "-");
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFileName));
                fileUploadedList.add(mpf.getOriginalFilename().replace(" ", "-"));
                path.setPathName(newFileName);
                path.setElementId(id);
                myPathService.addPlatePath(path);
            }catch(IOException e){
                logger.error(e);
            }
        }
        map.put("Status", 200);
        map.put("SucessfulList", fileUploadedList);
        return map;
    }
}
