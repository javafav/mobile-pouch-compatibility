package com.sastaybrands.mobiles;


import com.sastaybrands.mobiles.entity.Brand;
import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.repo.MobileRepository;

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
class MobileRepositoryTest {

    @Autowired
    private MobileRepository repo;
    @Autowired private EntityManager entityManager;

//    @BeforeEach
//    void setUp() {
//        // Arrange: Create test mobiles
//        brand = new Brand();
//        brand.setName("Samsung");
//        mobile1 = new Mobile("Galaxy S21", "Samsung", brand);
//
//
//        mobileRepository.save(mobile1);
//        mobileRepository.save(mobile2);
//        mobileRepository.save(duplicateMobile); // To test multiple results for the same model
//    }

    @Test
  public void testCreateMobile() {
    Brand brand = 	entityManager.find(Brand.class, 10);
    String mobileName = "A 32";
    String modelName = "SM32";
    Mobile samsungMobile = new Mobile();
    samsungMobile.setBrand(brand);
    samsungMobile.setModel(modelName);
    samsungMobile.setName(mobileName);
    
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
