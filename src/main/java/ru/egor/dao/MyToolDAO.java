package ru.egor.dao;

import ru.egor.entity.MyTool;

import java.util.List;

public interface MyToolDAO {

    List<MyTool> getMyTools();

    MyTool getToolById(int id);

    int addTool(MyTool myTool);

    void deleteToolById(int toolId);
}
