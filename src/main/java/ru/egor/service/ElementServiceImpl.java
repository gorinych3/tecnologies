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
import ru.egor.DAO.ElementDAO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import ru.egor.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@EnableTransactionManagement
@Transactional
public class ElementServiceImpl implements ElementService {

    private static final String FILE_PATH_PLATES = "C:/SaveImagesFromTechnology/Images/Plates/";
    private static final String FILE_PATH_TOOLS = "C:/SaveImagesFromTechnology/Images/Tools/";
    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);

    Gson gson = new Gson();

    @Autowired
    private ElementDAO elementDAO;

    @Override
    public List<Element> showAllElements() {
        return elementDAO.showAllElements();
    }

    @Override
    public List<Element> showElementByName(String nameElement) {
        return elementDAO.showElementByName(nameElement);
    }

    @Override
    public void addElement(Element element) {

    }

    @Override
    public void updateElement(Element element) {

    }

    @Override
    public void deleteElement(String name) {

    }

    @Override
    public String getMyTools() {
        return gson.toJson(elementDAO.getMyTools());
    }

    @Override
    public List<Plate> showPlates() {
        return elementDAO.showPlates();
    }

    @Override
    public int addPlate(Plate plate) {
        return elementDAO.addPlate(plate);

    }

    @Override
    public void addPlatePath(MyPath path) {
        elementDAO.addPathPlate(path);
    }


    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public List<MyPath> getMypathForOnePlate(int plateId) {
        return elementDAO.getMypathForOnePlate(plateId);
    }

    @Override
    public List<MyPath> getMypathAll() {
        return elementDAO.getMypathAll();
    }

    @Override
    public Plate getPlateByModel(String model) {
        return elementDAO.getPlateByModel(model);
    }

    @Override
    public Plate getPlateById(int id) {
        return elementDAO.getPlateById(id);
    }

    @Override
    public Map<String, Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath, String className) {
        logger.info("Start service 'fileUpload'");
        Map<String,Object> map = new HashMap<String,Object>();
        List<String> fileUploadedList = new ArrayList<String>();
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        String fileName = "";
        int some_id = 0;
        String newFileName = "";

        while(itr.hasNext()){
            MyPath path = new MyPath();
            mpf = request.getFile(itr.next());
            try{
                fileName = mpf.getOriginalFilename();
                some_id = Integer.parseInt(fileName.substring(fileName.lastIndexOf('-')+1,fileName.lastIndexOf('.')));
                newFileName = filePath+fileName.replace(" ", "-");
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFileName));
                fileUploadedList.add(mpf.getOriginalFilename().replace(" ", "-"));
                path.setPathName(newFileName);
                if(className.equals("plate")) {
                    path.setPlateId(some_id);
                }
                if(className.equals("tool")){
                    path.setMytoolId(some_id);
                }
                addPlatePath(path);
            }catch(IOException e){
                logger.error(e);
            }
        }
        map.put("Status", 200);
        map.put("SucessfulList", fileUploadedList);
        return map;
    }

    @Override
    public void deletePlateById(int plateId) {
        elementDAO.deletePlateById(plateId);
    }

    @Override
    public int addTool(String data) {
        logger.info("Start service 'addTool'");
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        MyTool myTool = new MyTool();
        myTool.setName(jsonObject.get("name").getAsString());
        myTool.setModel(jsonObject.get("model").getAsString());
        myTool.setType(jsonObject.get("type").getAsString());
        myTool.setPhoto(jsonObject.get("photo").getAsString());
        JsonArray plateNames = jsonObject.get("platesId").getAsJsonArray();
        Set<Plate> plates = new HashSet<>();
        for (int i = 0; i < plateNames.size(); i++){
            Plate plate = getPlateById(plateNames.get(i).getAsInt());
            plates.add(plate);
        }
        myTool.setPlates(plates);
        return elementDAO.addTool(myTool);
    }

    @Override
    public MyTool getToolById(int id) {
        return elementDAO.getToolById(id);
    }

    @Override
    public List<MyPath> getMypathForOneTool(int toolId) {
        return elementDAO.getMypathForOneTool(toolId);
    }

    @Override
    public void deleteToolById(int toolId) {
        elementDAO.deleteToolById(toolId);
    }

    @Override
    public int addDrill(String data) {
        logger.info("Start service 'addDrill'");
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        MyTool myTool = new MyTool();
        myTool.setName(jsonObject.get("name").getAsString());
        myTool.setModel(jsonObject.get("model").getAsString());
        myTool.setType(jsonObject.get("type").getAsString());
        myTool.setPhoto(jsonObject.get("photo").getAsString());
        myTool.setPlates(null);
        return elementDAO.addTool(myTool);
    }
}
