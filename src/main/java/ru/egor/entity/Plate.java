package ru.egor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

//    @ManyToMany
//    @JoinTable(name = "element_plate", joinColumns = @JoinColumn(name = "plateId"),
//            inverseJoinColumns = @JoinColumn(name = "elId"))
//    @Getter
//    @Setter
//    private Set<Element> elements;

//    @ManyToMany
//    @JoinTable(name = "tool_plate", joinColumns = @JoinColumn(name = "plateId"),
//            inverseJoinColumns = @JoinColumn(name = "toolId"))
//    @Getter
//    @Setter
//    private Set<MyTool> tools;

    @Override
    public String toString() {
        return "Plate{" +
                "plateId=" + plateId +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", photo='" + photo + '\'' +
//                ", elements=" + elements +
//                ", tools=" + tools +
                '}';
    }
}
