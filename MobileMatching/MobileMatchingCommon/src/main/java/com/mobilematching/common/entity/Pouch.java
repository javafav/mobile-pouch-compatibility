 package com.mobilematching.common.entity;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pouches")
public class Pouch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 45, unique = false)
    private String name;

  
    
    @Column(nullable = true, length = 64, unique = false)
    private String image;

    private double price;

    private String category; // Example: "Wallet Pouch", "Back Cover"
    private String brand; // Example: "Samsung", "Apple"

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pouch_mobile",
        joinColumns = @JoinColumn(name = "pouch_id"),
        inverseJoinColumns = @JoinColumn(name = "mobile_id") )
    private List<Mobile> compatibleMobiles = new ArrayList<>();

    // Default constructor (required by JPA)
    public Pouch() {}

    // Constructor with parameters
    public Pouch(String name, double price, String category, String brand) {
        this.name = name;
     
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

    
    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



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
        return "Pouch [name=" + name + ", price=" + price +
                ", category=" + category + ", brand=" + brand + "]";
    }
    
    @Transient
   	public String getImagePath() {
   		if (this.id == null) return "/images/image-thumbnail.png";
   		
   		return "/pouch-photos/"  + this.image;		
   	}
}
