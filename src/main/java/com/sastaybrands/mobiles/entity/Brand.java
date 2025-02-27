package com.sastaybrands.mobiles.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45, unique = true)
    private String name;

    @Column(nullable = false, length = 128)
    private String logo;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mobile> mobiles = new ArrayList<>();

    public Brand() {}

    public Brand(Long id) {
        this.id = id;
    }

    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
        this.logo = "default-logo.png";
    }

    public Brand(String name) {
        this.name = name;
        this.logo = "default-logo.png";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public List<Mobile> getMobiles() { return mobiles; }
    public void setMobiles(List<Mobile> mobiles) { this.mobiles = mobiles; }

    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", logo=" + logo + "]";
    }
}
