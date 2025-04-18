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

    @Column(nullable = false, length = 64, unique = true)
    private String name;
   
    @Column(nullable = false, length = 64, unique = true)
    private String model;
    
    @Column(nullable = false, length = 64, unique = true)
    private String photo;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    

    @ManyToMany(mappedBy = "compatibleMobiles")
    private List<Pouch> pouches = new ArrayList<>();

    public Mobile() {}

    public Mobile(String name, String model, Brand brand) {
        this.name = name;
        this.model = model;
        this.brand = brand;
    }

    // Utility methods
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

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }

    public List<Pouch> getPouches() { return pouches; }
    public void setPouches(List<Pouch> pouches) { this.pouches = pouches; }

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
    
	
	
	 @Override
	public String toString() {
		return "Mobile [id=" + id + ", name=" + name + ", model=" + model + "]";
	}

	@Transient
		public String getPhotoPath() {
			if (this.id == null) return "/images/image-thumbnail.png";
			
			return "/mobile-photos/"  + this.photo;		
		}
    
}
