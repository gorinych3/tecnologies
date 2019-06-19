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
import java.io.File;
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

    @Override
    public Element getElementById(int id) {
        return elementDAO.getElementById(id);
    }

    @Override
    public int addElement(String data) {
        logger.info("start service addElement");
        Element element = gson.fromJson(data, Element.class);
        System.out.println(element.toString());
        return elementDAO.addElement(element);
    }

    @Override
    public void updateElement(Element element) {
        elementDAO.updateElement(element);
    }

    @Override
    public void deleteElement(int id) {
        elementDAO.deleteElement(id);
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
                logger.info("имя файла перед записью = "+fileName);
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

    @Override
    public Map<String, Object> fileChangeElement(MultipartHttpServletRequest request, HttpServletResponse response, String filePath) {
        logger.info("Start service 'fileChangeElement'");
        Map<String,Object> map = new HashMap<String,Object>();
        List<String> fileUploadedList = new ArrayList<String>();
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        String fileName = "";
        int id = 0;
        String newFileName = "";

        while(itr.hasNext()){
            mpf = request.getFile(itr.next());
            try{
                fileName = mpf.getOriginalFilename();
                logger.info("имя файла перед изменением = "+fileName);
                id = Integer.parseInt(fileName.substring(fileName.lastIndexOf('-')+1,fileName.lastIndexOf('.')));
                newFileName = filePath+fileName.replace(" ", "-");
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFileName));
                fileUploadedList.add(mpf.getOriginalFilename().replace(" ", "-"));
            }catch(IOException e){
                logger.error(e);
            }
        }
        map.put("Status", 200);
        map.put("SucessfulList", fileUploadedList);
        return map;
    }

    @Override
    public void deleteFile(String fileName, String filePath) {
        JsonObject jsonObject = new JsonParser().parse(fileName).getAsJsonObject();
        String fullFileName = jsonObject.get("file").getAsString();
        System.out.println(fullFileName);
        final File file = new File(filePath+fullFileName);
        if(file.delete()) {
            logger.info("Фaйл yдaлeн");
            myPathService.deletePath(filePath+fullFileName);
        } else {
            logger.info("Фaйл yдaлить нe пoлyчилocь");
             }

    }
}
