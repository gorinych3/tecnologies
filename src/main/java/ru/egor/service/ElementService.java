package ru.egor.service;

import ru.egor.entity.*;
import java.util.List;


public interface ElementService {

    String getElements();

    List<Element> showElementByName(String nameElement);

    int addElement(String data);

    void updateElement(Element element);

    void deleteElement(String name);

}
