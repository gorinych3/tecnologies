package ru.egor.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "elements")
@Data
@NoArgsConstructor
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int elId;
    @Column
    private String nameElement;
    @Column
    private String idNumb;
    @Column
    private String technology;
    @Column
    private String program;
    @Column
    private String setup;
    @Column
    private String notation;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "element_tool", joinColumns = @JoinColumn(name = "elId"),
            inverseJoinColumns = @JoinColumn(name = "toolId"))
    private List<MyTool> tools;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "element_plate", joinColumns = @JoinColumn(name = "elId"),
            inverseJoinColumns = @JoinColumn(name = "plateId"))
    private Set<Plate> plates;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "element_machine", joinColumns = @JoinColumn(name = "elId"),
            inverseJoinColumns = @JoinColumn(name = "machId"))
    private Set<Machine> machines;
}
