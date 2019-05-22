package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.MyTool;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface MyToolService {

    String getMyTools();

    MyTool getToolById(int id);

    int addTool(String data);

    int addDrill(String data);

    void deleteToolById(int toolId);

    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath);
}
