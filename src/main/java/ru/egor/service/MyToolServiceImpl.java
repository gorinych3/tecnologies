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
import ru.egor.dao.MyToolDAO;
import ru.egor.entity.MyPath;
import ru.egor.entity.MyTool;
import ru.egor.entity.Plate;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional
public class MyToolServiceImpl implements MyToolService{

    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);

    private Gson gson;
    private MyToolDAO myToolDAO;
    private PlateService plateService;
    private MyPathService myPathService;

    @Autowired
    public MyToolServiceImpl(Gson gson, MyToolDAO myToolDAO, MyPathService myPathService, PlateService plateService) {
        this.gson = gson;
        this.myToolDAO = myToolDAO;
        this.myPathService = myPathService;
        this.plateService = plateService;
    }

    @Override
    public String getMyTools() {
        return gson.toJson(myToolDAO.getMyTools());
    }

    @Override
    public MyTool getToolById(int id) {
        return myToolDAO.getToolById(id);
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
            Plate plate = plateService.getPlateById(plateNames.get(i).getAsInt());
            plates.add(plate);
        }
        myTool.setPlates(plates);
        return myToolDAO.addTool(myTool);
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
        return myToolDAO.addTool(myTool);
    }

    @Override
    public void deleteToolById(int toolId) {
        myToolDAO.deleteToolById(toolId);
    }

    @Override
    public Map<String, Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath) {
        logger.info("Start service 'fileUpload'");
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
                path.setMytoolId(id);
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
