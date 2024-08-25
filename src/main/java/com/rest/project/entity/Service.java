package com.rest.project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="service")
@Getter
@Setter
@NoArgsConstructor
public class Service {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(mappedBy="services", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Landmark> landmarks;
}
