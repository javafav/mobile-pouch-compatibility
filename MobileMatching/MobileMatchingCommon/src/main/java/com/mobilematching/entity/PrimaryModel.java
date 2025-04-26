package com.mobilematching.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "primary_models")
public class PrimaryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Optional: Add more fields like brand, releaseYear etc.
    @Column(length = 45)
    private String brand;
    
    // Constructors
    public PrimaryModel() {}

    public PrimaryModel(String name) {
        this.name = name;
    }

    // Getters & Setters
    public Long getId() {
        return id;
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

	// toString
    @Override
    public String toString() {
        return "PrimaryModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
