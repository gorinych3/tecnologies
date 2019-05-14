package ru.egor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "path")
public class MyPath {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @Getter
    @Setter
    private int pathId;

    @Column
    @Getter
    @Setter
    private int plateId;

    @Column
    @Getter
    @Setter
    private int mytoolId;

    @Column
    @Getter
    @Setter
    private int elementId;

    @Column
    @Getter
    @Setter
    private String pathName;
}
