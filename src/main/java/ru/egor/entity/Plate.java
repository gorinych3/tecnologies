package ru.egor.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "plates")
public class Plate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @Getter
    @Setter
    private int plateId;

    @Column
    @Getter
    @Setter
    private String name;

    @Column
    @Getter
    @Setter
    private String model;

    @Column
    @Getter
    @Setter
    private String type;

    @Column
    @Getter
    @Setter
    private String photo;


    @Override
    public String toString() {
        return "Plate{" +
                "plateId=" + plateId +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", photo=" + photo +
                '}';
    }
}
