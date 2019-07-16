package ru.egor.dao;

import ru.egor.entity.*;

import java.util.List;

public interface ElementDAO {

    List<Element> getElements();

    List<Element> showElementByName(String name);

    Element getElementById(int id);

    int addElement(Element element);

    void deleteElement(int id);

    void updateElement(Element element);

}
