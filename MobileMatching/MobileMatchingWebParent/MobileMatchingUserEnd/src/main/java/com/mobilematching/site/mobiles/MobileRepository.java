package com.mobilematching.site.mobiles;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilematching.common.entity.Mobile;

public interface MobileRepository extends JpaRepository<Mobile, Long> {
    Mobile findByName(String name);
}