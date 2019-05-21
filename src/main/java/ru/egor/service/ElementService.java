package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface ElementService {

    //блок получения списков entity-------------------------------------------------------------------------------------

    List<Element> showAllElements();

    String getMyTools();

    List<Plate> showPlates();

    List<MyPath> getMypathAll();

    List<Machine> showMachine();


    //блок получения entity по заданному параметру----------------------------------------------------------------------

    List<Element> showElementByName(String nameElement);

    List<MyPath> getMypathForOnePlate(int plateId);

    Plate getPlateByModel(String model);

    Plate getPlateById(int id);

    MyTool getToolById(int id);

    List<MyPath> getMypathForOneTool(int toolId);



    //блок добавления entity--------------------------------------------------------------------------------------------

    void addElement(Element element);

    int addPlate(Plate plate);

    void addPlatePath(MyPath path);

    int addTool(String data);

    int addDrill(String data);

    int addMachine(Machine machine);



    //блок редактирования entity----------------------------------------------------------------------------------------

    void updateElement(Element element);


    //блок удаления entity----------------------------------------------------------------------------------------------

    void deleteElement(String name);

    void deletePlateById(int plateId);

    void deleteToolById(int toolId);


    //разное------------------------------------------------------------------------------------------------------------

    Path load(String filename);

    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath, String className);

}
