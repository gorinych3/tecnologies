package ru.egor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.DAO.ElementDAO;
import ru.egor.DAO.ElementDAOImpl;
import ru.egor.entity.Element;
import ru.egor.entity.MyTool;
import ru.egor.entity.Plate;

import java.util.List;

@Service
@EnableTransactionManagement
@Transactional
public class ElementServiceImpl implements ElementService {

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
    public void addPlate(Plate plate) {
        elementDAO.addPlate(plate);
    }
}
