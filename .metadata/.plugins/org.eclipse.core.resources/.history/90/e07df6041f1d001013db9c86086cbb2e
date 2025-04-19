package com.sastaybrands.mobiles.repo;

import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.Pouch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PouchRepository extends JpaRepository<Pouch, Long>, JpaSpecificationExecutor<Pouch> {
 
	@Query("SELECT p FROM Pouch p JOIN p.compatibleMobiles m WHERE m.model = :model OR m.name = :model")
	List<Pouch> findCompatiblePouchesByModel(@Param("model") String model);

	@Query("SELECT DISTINCT m FROM Mobile m JOIN m.pouches p WHERE p IN (SELECT p FROM Pouch p JOIN p.compatibleMobiles cm WHERE cm.name = ?1) AND m.name <> ?1")
	List<Mobile> findMobilesWithSamePouchExcludingInput(String mobileName);
	
	
	@Query("SELECT p FROM Pouch p JOIN p.compatibleMobiles m WHERE m.name LIKE %:keyword% OR m.model LIKE %:keyword% OR  m.brand.name LIKE %:keyword% ")
	Page<Pouch> findAll( String keyword, Pageable pageable);
	
//	@Query("SELECT p FROM Pouch p JOIN p.compatibleMobiles m WHERE " +
//		       "(:mobile IS NULL OR m.name LIKE %:mobile%) AND (:brand IS NULL OR m.brand.name LIKE %:brand%)")
//		Page<Pouch> findPouchesByMobileOrBrand(@Param("mobile") String mobile, 
//		                                       @Param("brand") String brand, 
//		                                       Pageable pageable);

	
//	 @Query("SELECT p FROM Pouch p")
//	    public Page<Pouch> findAll( Pageable pageable);
	
	
	
	// @Query("SELECT DISTINCT m FROM Mobile m JOIN m.pouches p WHERE p IN (SELECT p
	// FROM Pouch p JOIN p.compatibleMobiles cm WHERE cm.name = ?1)")

	// List<Mobile> findMobilesWithSamePouch(String mobileName);
	// List<Mobile> findByCompatibleMobiles(String mobile);
}
