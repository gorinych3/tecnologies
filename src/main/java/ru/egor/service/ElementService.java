package ru.egor.service;

import org.springframework.core.io.Resource;
import ru.egor.entity.Element;
import ru.egor.entity.MyTool;
import ru.egor.entity.MyPath;
import ru.egor.entity.Plate;

import java.nio.file.Path;
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

    void addPlatePath(MyPath path);

    Resource loadAsResource(String filename);

    Path load(String filename);

    List<MyPath> getMypathForOneElement(int plateId);

    List<MyPath> getMypathAll();

    Plate getPlateByModel(String model);

    Plate getPlateById(int id);
}
