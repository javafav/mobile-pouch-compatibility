package com.mobilematching.entity;

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

    @ManyToOne
    @JoinColumn(name = "primary_model_id", nullable = false)
    private PrimaryModel primaryModel;

    @ManyToMany
    @JoinTable(
        name = "glass_protector_compatibility",
        joinColumns = @JoinColumn(name = "glass_protector_id"),
        inverseJoinColumns = @JoinColumn(name = "mobile_id")
    )
    private Set<Mobile> compatibleModels = new HashSet<>();

    // Constructors
    public GlassProtector() {}

    public GlassProtector(String name, PrimaryModel primaryModel) {
        this.name = name;
        this.primaryModel = primaryModel;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PrimaryModel getPrimaryModel() {
        return primaryModel;
    }

    public void setPrimaryModel(PrimaryModel primaryModel) {
        this.primaryModel = primaryModel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Mobile> getCompatibleModels() {
        return compatibleModels;
    }

    public void setCompatibleModels(Set<Mobile> compatibleModels) {
        this.compatibleModels = compatibleModels;
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
}
