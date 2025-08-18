package com.mobilematching.site.mobiles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilematching.common.entity.Mobile;

public interface MobileRepository extends JpaRepository<Mobile, Long> {
    Mobile findByName(String name);
    
    Optional<Mobile> findFirstByNameIgnoreCaseContaining(String name);
}