package com.sastaybrands.mobiles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Enumerated(EnumType.STRING)
    private Material material;

    private double price;

    private String category; // Example: "Wallet Pouch", "Back Cover"
    private String brand; // Example: "Samsung", "Apple"

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pouch_mobile",
        joinColumns = @JoinColumn(name = "pouch_id"),
        inverseJoinColumns = @JoinColumn(name = "mobile_id")
    )
    @JsonIgnore // Prevents infinite loop in JSON serialization
    private List<Mobile> compatibleMobiles = new ArrayList<>();

    // Default constructor (required by JPA)
    public Pouch() {}

    // Constructor with parameters
    public Pouch(String name, Material material, double price, String category, String brand) {
        this.name = name;
        this.material = material;
        this.price = price;
        this.category = category;
        this.brand = brand;
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

    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public List<Mobile> getCompatibleMobiles() { return compatibleMobiles; }
    public void setCompatibleMobiles(List<Mobile> compatibleMobiles) { this.compatibleMobiles = compatibleMobiles; }

    @Override
    public String toString() {
        return "Pouch [name=" + name + ", material=" + material + ", price=" + price +
                ", category=" + category + ", brand=" + brand + "]";
    }
}
