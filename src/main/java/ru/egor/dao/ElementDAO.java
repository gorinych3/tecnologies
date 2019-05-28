package ru.egor.dao;

import ru.egor.entity.*;

import java.util.List;

public interface ElementDAO {

    List<Element> getElements();

    List<Element> showElementByName(String name);

    int addElement(Element element);

    void deleteElement(String name);

    void updateElement(Element element);

}
