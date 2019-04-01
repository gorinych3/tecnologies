package ru.egor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.DAO.ElementDAO;
import ru.egor.DAO.ElementDAOImpl;
import ru.egor.entity.*;

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
        //добавляем в бд пластину
//        Plate plate = new Plate();
//        plate.setName(predPlate.getName());
//        plate.setModel(predPlate.getModel());
//        plate.setType(predPlate.getType());
//        plate.setPhoto(predPlate.getName()+"_фото");
//        elementDAO.addPlate(plate);
//
//        //добавляем в бд пути к файлам по имени файла (переделать на абсолютный путь)
//        int plateId = elementDAO.getPlateByModel(plate.getModel()).getPlateId();
//        Path path = new Path();
//        path.setPlateId(plateId);
//        for(int i = 0; i < predPlate.getPhoto().size(); i++){
//            path.setPathName(predPlate.getPhoto().get(i).getName());
//            elementDAO.addPathPlate(path);
//        }

        //тут будут записываться файлы в файловую систему
        elementDAO.addPlate(plate);

    }


}
