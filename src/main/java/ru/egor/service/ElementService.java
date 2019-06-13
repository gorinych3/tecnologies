package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public interface ElementService {

    String getElements();

    Element getElementById(int id);

    List<Element> showElementByName(String nameElement);

    int addElement(String data);

    void updateElement(Element element);

    void deleteElement(int id);

    Map<String,Object> fileUploadElement(MultipartHttpServletRequest request, HttpServletResponse response, String filePath);

    Map<String,Object> fileChangeElement(MultipartHttpServletRequest request, HttpServletResponse response, String filePath);

    void deleteFile(String fileName, String filePath);



}
