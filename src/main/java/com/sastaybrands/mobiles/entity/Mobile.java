package com.sastaybrands.mobiles.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mobiles")
public class Mobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String model;

    @OneToMany(mappedBy = "mobile", cascade = CascadeType.ALL)
    private List<Pouch> compatiblePouches = new ArrayList<>();

    public Mobile() {
    }

    public Mobile(String name, String brand, String model) {

        this.name = name;
        this.brand = brand;
        this.model = model;
    }

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Pouch> getCompatiblePouches() {
        return compatiblePouches;
    }

    public void setCompatiblePouches(List<Pouch> compatiblePouches) {
        this.compatiblePouches = compatiblePouches;
    }
}

