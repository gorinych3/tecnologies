package ru.egor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @Getter
    @Setter
    private int machId;

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
    private String idNumber;

    @Column
    @Getter
    @Setter
    private String photo;


    @Override
    public String toString() {
        return "Machine{" +
                "machId=" + machId +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
