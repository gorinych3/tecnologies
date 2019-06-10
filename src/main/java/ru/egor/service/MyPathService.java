package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.MyPath;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface MyPathService {

    List<MyPath> getMypathAll();

    List<MyPath> getMypathForOneElement(int elId);

    List<MyPath> getMypathForOnePlate(int plateId);

    List<MyPath> getMypathForOneTool(int toolId);

    List<MyPath> getMypathForOneMachine(int machId);

    void addPlatePath(MyPath path);

    void deletePath(String fileName);

}
