package ru.egor.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "plates")
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int plateId;
    @Column
    private String name;
    @Column
    private String model;
    @Column
    private String type;
    @Column
    private String photo;
}
