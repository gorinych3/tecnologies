package ru.egor.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.egor.entity.Machine;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MachineService {

    List<Machine> showMachine();

    Machine getMachineById(int id);

    int addMachine(Machine machine);

    void deleteMachineById(int id);

    Map<String,Object> fileUpload(MultipartHttpServletRequest request, HttpServletResponse response, String filePath);
}
