package ru.egor.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;

@Component
@Data
public class FileProperties {
    @Resource(name = "myProperties")
    private Properties fileProperties;
    private String toolPath;
    private String platesPath;
    private String machinesPath;
    private String elementsPath;

    @PostConstruct
    public void init() {
        toolPath = fileProperties.getProperty("file.tools.toolPath");
        platesPath = fileProperties.getProperty("file.tools.platePath");
        machinesPath = fileProperties.getProperty("file.tools.machinesPath");
        elementsPath = fileProperties.getProperty("file.tools.elementsPath");
    }
}
