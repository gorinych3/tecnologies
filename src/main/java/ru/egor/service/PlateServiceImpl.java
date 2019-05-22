package ru.egor.service;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.dao.PlateDAO;
import ru.egor.entity.MyPath;
import ru.egor.entity.Plate;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional
public class PlateServiceImpl implements PlateService {

    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);

    private Gson gson;
    private PlateDAO plateDAO;
    private MyPathService myPathService;

    public PlateServiceImpl(Gson gson, PlateDAO plateDAO, MyPathService myPathService) {
        this.gson = gson;
        this.plateDAO = plateDAO;
        this.myPathService = myPathService;
    }

    @Override
    public List<Plate> showPlates() {
        return plateDAO.showPlates();
    }

    @Override
    public Plate getPlateByModel(String model) {
        return plateDAO.getPlateByModel(model);
    }

    @Override
    public Plate getPlateById(int id) {
        return plateDAO.getPlateById(id);
    }

    @Override
    public int addPlate(Plate plate) {
        return plateDAO.addPlate(plate);
    }

    @Override
    public void deletePlateById(int plateId) {
        plateDAO.deletePlateById(plateId);
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
                path.setPlateId(id);
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
