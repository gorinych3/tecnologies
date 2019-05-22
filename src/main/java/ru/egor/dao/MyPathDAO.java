package ru.egor.dao;

import ru.egor.entity.MyPath;

import java.util.List;

public interface MyPathDAO {

    List<MyPath> getMypathAll();

    List<MyPath> getMypathForOnePlate(int plateId);

    List<MyPath> getMypathForOneTool(int toolId);

    List<MyPath> getMypathForOneMachine(int machId);

    void addPathPlate(MyPath path);


}
