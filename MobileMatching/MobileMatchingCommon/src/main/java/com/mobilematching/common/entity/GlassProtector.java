package com.mobilematching.common.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "glass_protectors")
public class GlassProtector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45, unique = true)
    private String name;
    
    
    @Column(nullable = false, length = 128)
    private String image;
    
    private double price;

    @ManyToOne
    @JoinColumn(name = "primary_model_id", nullable = true)
    private PrimaryModel primaryModel;


    
    
    @ManyToMany
    @JoinTable(
        name = "glass_protector_compatibility",
        joinColumns = @JoinColumn(name = "glass_protector_id"),
        inverseJoinColumns = @JoinColumn(name = "mobile_id")
    )
    private Set<Mobile> compatibleMobiles = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    
    // Constructors
    public GlassProtector() {}

    public GlassProtector(String name, PrimaryModel primaryModel) {
        this.name = name;
        
        if (primaryModel == null) {
            PrimaryModel defaultModel = new PrimaryModel();
            defaultModel.setName("No Primary Model");
            this.primaryModel = defaultModel;
        } else {
            this.primaryModel = primaryModel;
        }
       
        // Set brand based on primary model (optional)
        if (primaryModel != null && primaryModel.getBrand() != null) {
            this.brand = primaryModel.getBrand();
        }
    }

    // Getters & Setters
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
    
    

    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public PrimaryModel getPrimaryModel() {
        return primaryModel;
    }

    public void setPrimaryModel(PrimaryModel primaryModel) {
        this.primaryModel = primaryModel;
    }

    public Set<Mobile> getCompatibleMobiles() {
        return compatibleMobiles;
    }

    public void setCompatibleMobiles(Set<Mobile> compatibleMobiles) {
        this.compatibleMobiles = compatibleMobiles;
    }
    
    public Brand getBrand() {
        return brand;
    }
    
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    
    // Helper methods
    public void addCompatibleMobile(Mobile mobile) {
        this.compatibleMobiles.add(mobile);
    }
    
    public void removeCompatibleMobile(Mobile mobile) {
        this.compatibleMobiles.remove(mobile);
    }
    
    public boolean isCompatibleWith(Mobile mobile) {
        return this.compatibleMobiles.contains(mobile);
    }
    
    
    

    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GlassProtector)) return false;
        GlassProtector that = (GlassProtector) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "GlassProtector{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", primaryModel=" + (primaryModel != null ? primaryModel.getName() : "null") +
                ", compatibleModels=" + compatibleMobiles.size() +
                ", brand='" + brand + '\'' +
                '}';
    }
    
    @Transient
  	public String getImagePath() {
  		if (this.id == null) return "/images/image-thumbnail.png";
  		
  		return "/glass_protector/" + this.id + "/" + this.image;		
  	}
}