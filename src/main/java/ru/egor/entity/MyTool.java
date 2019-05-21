package ru.egor.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tools")
public class MyTool {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @Getter
    @Setter
    private int toolId;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tool_plate", joinColumns = @JoinColumn(name = "toolId"),
            inverseJoinColumns = @JoinColumn(name = "plateId"))
    @Getter
    @Setter
    private Set<Plate> plates;

    @Override
    public String toString() {
        return "MyTool{" +
                "toolid=" + toolId +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", photo='" + photo + '\'' +
                ", plates=" + getPlates() +
                '}';
    }
}
