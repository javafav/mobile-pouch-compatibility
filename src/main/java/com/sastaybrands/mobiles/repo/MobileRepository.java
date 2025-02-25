package com.sastaybrands.mobiles.repo;

import com.sastaybrands.mobiles.entity.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MobileRepository extends JpaRepository<Mobile, Long> {
    List<Mobile> findByModel(String model);

}

