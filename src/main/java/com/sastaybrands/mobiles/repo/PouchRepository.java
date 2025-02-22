package com.sastaybrands.mobiles.repo;

import com.sastaybrands.mobiles.entity.Pouch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PouchRepository extends JpaRepository<Pouch, Long> {
    @Query("SELECT p FROM Pouch p JOIN p.compatibleMobiles m WHERE m.model = :model")
    List<Pouch> findCompatiblePouchesByModel(@Param("model") String model);

    List<Pouch> findByCompatibleMobiles_Id(Long mobileId);
}
