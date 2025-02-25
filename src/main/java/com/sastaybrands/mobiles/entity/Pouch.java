package com.sastaybrands.mobiles.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pouches")
public class Pouch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String material;
    private double price;

    @ManyToMany
    @JoinTable(
            name = "pouch_mobile",  // Explicitly defining the join table
            joinColumns = @JoinColumn(name = "pouch_id"),
            inverseJoinColumns = @JoinColumn(name = "mobile_id")
    )
    private List<Mobile> compatibleMobiles = new ArrayList<>();

    public Pouch() {
    }

    public Pouch(String name, String material, double price) {
        this.name = name;
        this.material = material;
        this.price = price;
    }

    // Utility method to maintain bidirectional relationship
    public void addMobile(Mobile mobile) {
        this.compatibleMobiles.add(mobile);
        mobile.getPouches().add(this);
    }

    public void removeMobile(Mobile mobile) {
        this.compatibleMobiles.remove(mobile);
        mobile.getPouches().remove(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<Mobile> getCompatibleMobiles() { return compatibleMobiles; }
    public void setCompatibleMobiles(List<Mobile> compatibleMobiles) { this.compatibleMobiles = compatibleMobiles; }
}
