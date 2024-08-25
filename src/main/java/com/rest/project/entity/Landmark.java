package com.rest.project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="landmark")
@Getter
@Setter
@NoArgsConstructor
public class Landmark {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Integer id;

    @Column(name="name", updatable = false)
    private String name;

    @Column(name="create_date", updatable = false)
    private Date createDate;

    @Column(name="description")
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="landmark_type", updatable = false)
    private LandmarkType landmarkType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "locality_id", referencedColumnName = "id", updatable = false)
    private Locality locality;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="landmark_service",
            joinColumns = @JoinColumn(name="landmark_id"),
            inverseJoinColumns = @JoinColumn(name="service_id")
    )
    private List<Service> services;

}
