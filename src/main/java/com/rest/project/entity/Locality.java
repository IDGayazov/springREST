package com.rest.project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="locality")
@Getter
@Setter
@NoArgsConstructor
public class Locality {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="population")
    private int population;

    @Column(name="has_metro")
    private boolean hasMetro;

    @OneToMany(mappedBy="locality")
    private List<Landmark> landmarks;

}
