package ru.egor.dao;

import ru.egor.entity.Machine;

import java.util.List;

public interface MachineDAO {

    List<Machine> showMachine();

    Machine getMachineById(int id);

    int addMachine(Machine machine);

    void deleteMachineById(int machId);
}
