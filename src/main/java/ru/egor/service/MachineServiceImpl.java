package ru.egor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.dao.MachineDAO;
import ru.egor.entity.Machine;
import ru.egor.entity.MyPath;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional
public class MachineServiceImpl implements MachineService {

    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);

    private MachineDAO machineDAO;
    private MyPathService myPathService;

    @Autowired
    public MachineServiceImpl(MachineDAO machineDAO, MyPathService myPathService) {
        this.machineDAO = machineDAO;
        this.myPathService = myPathService;
    }

    @Override
    public List<Machine> showMachine() {
        return machineDAO.showMachine();
    }

    @Override
    public Machine getMachineById(int id) {
        return machineDAO.getMachineById(id);
    }

    @Override
    public int addMachine(Machine machine) {
        return machineDAO.addMachine(machine);
    }

    @Override
    public void deleteMachineById(int id) {
        machineDAO.deleteMachineById(id);
    }

    @Override
    public Map<String, Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath) {
        logger.info("Start service 'fileUpload'");
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> fileUploadedList = new ArrayList<String>();
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        String fileName;
        int id = 0;
        String newFileName;

        while (itr.hasNext()) {
            MyPath path = new MyPath();
            mpf = request.getFile(itr.next());
            try {
                fileName = mpf.getOriginalFilename();
                id = Integer.parseInt(fileName.substring(fileName.lastIndexOf('-') + 1, fileName.lastIndexOf('.')));
                newFileName = filePath + fileName.replace(" ", "-");
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFileName));
                fileUploadedList.add(mpf.getOriginalFilename().replace(" ", "-"));
                path.setPathName(newFileName);
                path.setMachineId(id);
                myPathService.addPlatePath(path);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        map.put("Status", 200);
        map.put("SuccessfulList", fileUploadedList);
        return map;
    }
}
