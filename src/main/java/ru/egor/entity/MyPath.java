package ru.egor.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "path")
public class MyPath {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int pathId;
    @Column
    private int plateId;
    @Column
    private int myToolId;
    @Column
    private int elementId;
    @Column
    private int machineId;
    @Column
    private String pathName;
}
