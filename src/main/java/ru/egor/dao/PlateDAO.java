package ru.egor.dao;

import ru.egor.entity.Plate;

import java.util.List;

public interface PlateDAO {

    List<Plate> showPlates();

    Plate getPlateByModel(String model);

    Plate getPlateById(int id);

    int addPlate(Plate plate);

    void deletePlateById(int plateId);
}
