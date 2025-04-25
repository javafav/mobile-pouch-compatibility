package com.mobilematching.admin.glassprotector;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilematching.entity.PrimaryModel;

public interface PrimaryModelRepository extends JpaRepository<PrimaryModel, Long> {
	Optional<PrimaryModel> findByName(String name);
}
