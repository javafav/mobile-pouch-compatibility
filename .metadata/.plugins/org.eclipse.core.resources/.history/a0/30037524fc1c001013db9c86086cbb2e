package com.mobilematching.admin.brands;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilemacthing.entity.Brand;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT COUNT(b) FROM Brand b WHERE b.id = ?1")
    public Long count(Long id);

    public Brand findByName(String name);


    @Query("SELECT NEW Brand(b.id, b.name) FROM Brand b ORDER By b.name ASC")
    public List<Brand> findAll();


    @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
    public Page<Brand> findAll( String keyword, Pageable pageable);
}
