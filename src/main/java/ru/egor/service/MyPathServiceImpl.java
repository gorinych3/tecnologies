package ru.egor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.dao.MyPathDAO;
import ru.egor.entity.MyPath;
import java.util.List;


@Service
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional
public class MyPathServiceImpl implements MyPathService {
    private final static Logger logger = Logger.getLogger(ElementServiceImpl.class);
    private MyPathDAO myPathDAO;

    @Autowired
    public MyPathServiceImpl(MyPathDAO myPathDAO) {
        this.myPathDAO = myPathDAO;
    }

    @Override
    public List<MyPath> getMypathAll() {
        return myPathDAO.getMypathAll();
    }

    @Override
    public List<MyPath> getMypathForOneElement(int elId) {
        return myPathDAO.getMypathForOneElement(elId);
    }

    @Override
    public List<MyPath> getMypathForOnePlate(int plateId) {
        return myPathDAO.getMypathForOnePlate(plateId);
    }

    @Override
    public List<MyPath> getMypathForOneTool(int toolId) {
        return myPathDAO.getMypathForOneTool(toolId);
    }

    @Override
    public List<MyPath> getMypathForOneMachine(int machId) {
        return myPathDAO.getMypathForOneMachine(machId);
    }

    @Override
    public void addPlatePath(MyPath path) {
        myPathDAO.addPathPlate(path);
    }

    @Override
    public void deletePath(String fileName) {
        myPathDAO.deletePath(fileName);
    }
}
