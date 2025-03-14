package com.sastaybrands.mobiles;


import com.sastaybrands.mobiles.entity.Brand;
import com.sastaybrands.mobiles.entity.Material;
import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.repo.MobileRepository;
import com.sastaybrands.mobiles.repo.PouchRepository;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class PouchRepositoryTest {

    @Autowired
    private PouchRepository repo;
    @Autowired private EntityManager entityManager;

    @Test
  public void testCreatePouch() {
    Mobile smart9 = 	entityManager.find(Mobile.class, 181);
    Mobile hot50i = 	entityManager.find(Mobile.class, 182);
    Mobile go1 = 	entityManager.find(Mobile.class, 183);
    
    Pouch pouch = new Pouch();
   
    pouch.addMobile(go1);
    pouch.addMobile(hot50i);
    pouch.addMobile(smart9);
 
    pouch.setMaterial(Material.LEATHER);
    pouch.set
    
    
    
    repo.save(samsungMobile);
    
    Mobile savedProduct = repo.save(samsungMobile);
	
	assertThat(savedProduct).isNotNull();
	assertThat(savedProduct.getId()).isGreaterThan(0);
    }

	@Test
	public void testFindByName() {
		
		String mobileName = "A 32";
		Mobile mobile = repo.findByName(mobileName);
		System.out.println(mobile);
		assertThat(mobile.getName()).isEqualTo(mobileName);

	}
	
	@Test
	public void testListAll() {
		Iterable<Mobile> listMobiles = repo.findAll();
		listMobiles.forEach(brand  -> System.out.println(brand.getName()));
	}
	

	@Test
	public void testUpdateMobile(){
		Long mobileId = 1L;
		Mobile mobile = repo.findById(mobileId).get();
		mobile.setName("Samsung A 32 128GB");
		Mobile savedMobile = repo.save(mobile);
		
		assertThat(savedMobile.getName()).isEqualTo("Samsung A 32 128GB");
		
	}
	
	@Test
	public void testDeleteMobiles() {
		repo.deleteById(1L);
		
		Optional<Mobile> mobile = repo.findById(1L);
		assertThat(mobile).isEmpty();
	}
	
	
	
  
}
