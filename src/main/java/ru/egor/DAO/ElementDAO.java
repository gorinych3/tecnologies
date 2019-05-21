package ru.egor.DAO;

import ru.egor.entity.*;

import java.util.List;

public interface ElementDAO {

    //блок получения списка entity--------------------------------------------------------------------------------------
    List<Element> showAllElements();

    List<MyTool> getMyTools();

    List<Plate> showPlates();

    List<MyPath> getMypathAll();

    List<Machine> showMachine();


    //блок получения entity по заданным параметрам----------------------------------------------------------------------
    List<Element> showElementByName(String name);

    Plate getPlateByModel(String model);

    Plate getPlateById(int id);

    MyTool getToolById(int id);

    Machine getMachineById(int id);

    List<MyPath> getMypathForOnePlate(int plateId);

    List<MyPath> getMypathForOneTool(int toolId);

    List<MyPath> getMypathForOneMachine(int machId);


    //блок добавления entity--------------------------------------------------------------------------------------------
    void addElement(Element element);

    int addPlate(Plate plate);

    void addPathPlate(MyPath path);

    int addTool(MyTool myTool);

    int addMachine(Machine machine);


    //блок удаления entity----------------------------------------------------------------------------------------------
    void deleteElement(String name);

    void deletePlateById(int plateId);

    void deleteToolById(int toolId);

    void deleteMachineById(int machId);



    //блок редактирования entity----------------------------------------------------------------------------------------
    void updateElement(Element element);

}
