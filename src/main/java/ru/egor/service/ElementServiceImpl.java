package ru.egor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import ru.egor.DAO.ElementDAOImpl;
import ru.egor.OtherClasses.StorageFileNotFoundException;
import ru.egor.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.util.*;

@Service
@EnableTransactionManagement
@Transactional
public class ElementServiceImpl implements ElementService {

    private static final String FILE_PATH = "C:/SaveImagesFromTechnology/Images/";
    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);

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
    public List<MyTool> getMyTools() {
        return elementDAO.getMyTools();
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
    public List<MyPath> getMypathForOneElement(int plateId) {
        return elementDAO.getMypathForOneElement(plateId);
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
    public Map<String, Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
        logger.info("Start service 'fileUpload'");
        Map<String,Object> map = new HashMap<String,Object>();
        List<String> fileUploadedList = new ArrayList<String>();
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        String fileName = "";
        int plate_id = 0;
        String newFileName = "";

        while(itr.hasNext()){
            MyPath path = new MyPath();
            mpf = request.getFile(itr.next());
            try{
                fileName = mpf.getOriginalFilename();
                plate_id = Integer.parseInt(fileName.substring(fileName.lastIndexOf('-')+1,fileName.lastIndexOf('.')));
                newFileName = FILE_PATH+fileName.replace(" ", "-");
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFileName));
                fileUploadedList.add(mpf.getOriginalFilename().replace(" ", "-"));
                path.setPathName(newFileName);
                path.setPlateId(plate_id);
                addPlatePath(path);
            }catch(IOException e){
                logger.error(e);
            }
        }
        map.put("Status", 200);
        map.put("SucessfulList", fileUploadedList);
        return map;
    }
}
