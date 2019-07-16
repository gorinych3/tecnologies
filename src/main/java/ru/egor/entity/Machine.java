package ru.egor.entity;

import lombok.Data;
import javax.persistence.*;


@Entity
@Data
@Table(name = "machines")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int machId;
    @Column
    private String name;
    @Column
    private String model;
    @Column
    private String idNumber;
    @Column
    private String photo;
}
