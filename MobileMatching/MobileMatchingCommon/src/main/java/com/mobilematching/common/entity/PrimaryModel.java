package com.mobilematching.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "primary_models")

public class PrimaryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    // Constructors
    public PrimaryModel() {}

    public PrimaryModel(String name, Brand brand) {
        this.name = name;
        this.brand = brand;
    }

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Brand getBrand() { return brand; }

    public void setBrand(Brand brand) { this.brand = brand; }
}