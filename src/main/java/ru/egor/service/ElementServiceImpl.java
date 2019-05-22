package ru.egor.service;

import com.google.gson.Gson;
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

    @Autowired
    public ElementServiceImpl(Gson gson, ElementDAO elementDAO) {
        this.gson = gson;
        this.elementDAO = elementDAO;
    }


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

}
