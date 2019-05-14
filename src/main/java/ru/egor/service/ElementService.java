package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.Element;
import ru.egor.entity.MyPath;
import ru.egor.entity.Plate;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface ElementService {

    List<Element> showAllElements();

    List<Element> showElementByName(String nameElement);

    void addElement(Element element);

    void updateElement(Element element);

    void deleteElement(String name);

    String getMyTools();

    List<Plate> showPlates();

    int addPlate(Plate plate);

    void addPlatePath(MyPath path);

    Path load(String filename);

    List<MyPath> getMypathForOneElement(int plateId);

    List<MyPath> getMypathAll();

    Plate getPlateByModel(String model);

    Plate getPlateById(int id);

    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath, String className);

    void deletePlateById(int plateId);

    int addTool(String data);
}
