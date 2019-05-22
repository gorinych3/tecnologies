package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.Plate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface PlateService {

    List<Plate> showPlates();

    Plate getPlateByModel(String model);

    Plate getPlateById(int id);

    int addPlate(Plate plate);

    void deletePlateById(int plateId);

    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath);
}
