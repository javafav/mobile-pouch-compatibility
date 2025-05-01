package com.mobilematching.admin.glassprotector;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mobilematching.entity.GlassProtector;
import com.mobilematching.entity.Pouch;

public interface GlassProtectorRepository extends JpaRepository<GlassProtector, Long> {
	
//	 @Query("SELECT g FROM GlassProtector g WHERE g.name LIKE %?1%")
//	    public Page<GlassProtector> findAll(String keyword, Pageable pageable);

	    public Long countById(Long id);
	    
	    
	    @Query("SELECT gp FROM GlassProtector gp JOIN gp.compatibleMobiles m WHERE m.name LIKE %:keyword% OR m.model LIKE %:keyword% OR  m.brand.name LIKE %:keyword% ")
		Page<GlassProtector> findAll( String keyword, Pageable pageable);

}
