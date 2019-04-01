package ru.egor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.File;
import java.util.List;

@Getter
@Setter
public class PredPlate {

//    public PredPlate(int plateId, String name, String model, String type, List<String> pathes) {
//        this.plateId = plateId;
//        this.name = name;
//        this.model = model;
//        this.type = type;
//        this.pathes = pathes;
//    }

    private int plateId;

    private String name;

    private String model;

    private String type;

    private List<String> pathes;



    @Override
    public String toString() {
        return "PredPlate{" +
                "plateId=" + plateId +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", pathes=" + pathes +
                '}';
    }
}
