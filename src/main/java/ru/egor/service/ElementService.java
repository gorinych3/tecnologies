package ru.egor.service;

import ru.egor.entity.*;
import java.util.List;


public interface ElementService {

    List<Element> showAllElements();

    List<Element> showElementByName(String nameElement);

    void addElement(Element element);

    void updateElement(Element element);

    void deleteElement(String name);

}
