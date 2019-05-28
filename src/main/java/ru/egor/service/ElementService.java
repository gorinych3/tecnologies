package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public interface ElementService {

    String getElements();

    List<Element> showElementByName(String nameElement);

    int addElement(String data);

    void updateElement(Element element);

    void deleteElement(String name);

    Map<String,Object> fileUploadElement(MultipartHttpServletRequest request, HttpServletResponse response, String filePath);

}
