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

    @ManyToMany(mappedBy = "compatibleMobiles")
    private List<Pouch> pouches = new ArrayList<>();

    public Mobile() {
    }

    public Mobile(String name, String brand, String model) {
        this.name = name;
        this.brand = brand;
        this.model = model;
    }

    // Utility method to maintain bidirectional relationship
    public void addPouch(Pouch pouch) {
        this.pouches.add(pouch);
        pouch.getCompatibleMobiles().add(this);
    }

    public void removePouch(Pouch pouch) {
        this.pouches.remove(pouch);
        pouch.getCompatibleMobiles().remove(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public List<Pouch> getPouches() { return pouches; }
    public void setPouches(List<Pouch> pouches) { this.pouches = pouches; }
}
