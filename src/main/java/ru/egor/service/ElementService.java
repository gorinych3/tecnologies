package ru.egor.service;

import ru.egor.entity.Element;
import ru.egor.entity.MyTool;
import ru.egor.entity.Path;
import ru.egor.entity.Plate;

import java.util.List;

public interface ElementService {

    List<Element> showAllElements();

    List<Element> showElementByName(String nameElement);

    void addElement(Element element);

    void updateElement(Element element);

    void deleteElement(String name);

    List<MyTool> getMyTools();

    List<Plate> showPlates();

    int addPlate(Plate plate);

    void addPlatePath(Path path);
}
