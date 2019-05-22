package ru.egor.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "tools")
public class MyTool {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int toolId;

    @Column
    private String name;

    @Column
    private String model;

    @Column
    private String type;

    @Column
    private String photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tool_plate", joinColumns = @JoinColumn(name = "toolId"),
            inverseJoinColumns = @JoinColumn(name = "plateId"))
    private Set<Plate> plates;

}
