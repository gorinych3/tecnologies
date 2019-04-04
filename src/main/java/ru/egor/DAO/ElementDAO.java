package ru.egor.DAO;

import ru.egor.entity.Element;
import ru.egor.entity.MyTool;
import ru.egor.entity.Path;
import ru.egor.entity.Plate;

import java.util.List;

public interface ElementDAO {

    List<Element> showAllElements();

    List<Element> showElementByName(String name);

    void addElement(Element element);

    void updateElement(Element element);

    void deleteElement(String name);

    List<MyTool> getMyTools();

    List<Plate> showPlates();

    int addPlate(Plate plate);

    Plate getPlateByModel(String model);

    void addPathPlate(Path path);
}
