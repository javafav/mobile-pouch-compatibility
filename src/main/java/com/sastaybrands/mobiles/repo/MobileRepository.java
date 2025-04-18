package com.sastaybrands.mobiles.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sastaybrands.mobiles.entity.Mobile;

public interface MobileRepository extends JpaRepository<Mobile, Long> {
  //  List<Mobile> findByModel(String model);


    @Query("SELECT COUNT(m) FROM Mobile m WHERE m.id = ?1")
    public Long count(Long id);
	
	
    @Query("SELECT m FROM Mobile m WHERE m.name LIKE %?1% OR m.model LIKE %?1% OR m.brand.name LIKE %?1%")
    public Page<Mobile> findAll( String keyword, Pageable pageable);
	
    @Query("SELECT m FROM Mobile m WHERE m.name = ?1")
	public Mobile findByName(String mobileName);
    
    @Query("SELECT m FROM Mobile m WHERE m.model = ?1")
   	public Mobile findByModel(String modelName);
    
    @Query("SELECT m FROM Mobile m ORDER BY m.name ASC")
    public List<Mobile> findAll();
    
    List<Mobile> findAllById(Iterable<Long> ids);

}

