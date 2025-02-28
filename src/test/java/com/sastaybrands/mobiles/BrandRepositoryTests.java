package com.sastaybrands.mobiles;

import com.sastaybrands.mobiles.entity.Brand;
import com.sastaybrands.mobiles.repo.BrandRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class BrandRepositoryTests {

    @Autowired
    private BrandRepository repo;

    private Brand brand;
   @Autowired private EntityManager entityManager;
    @BeforeEach
    void setUp() {
//        brand = new Brand();
//        brand.setName("Vivo");
//        brand.setLogo("vivo.png");
//        repo.save(brand);

    }

    @Test
    void testCreateBrand() {


        Brand savedBrand = repo.save(brand);

        assertThat(savedBrand.getId()).isNotNull();
    }

    @Test
    void testListAllBrands() {
        Iterable<Brand> brands = repo.findAll();

        assertThat(brands).isNotEmpty();
    }

    @Test
    void testUpdateBrand() {

        Brand brand = new Brand(2L);
        Optional<Brand> optionalBrand = repo.findById(brand.getId());

        assertThat(optionalBrand).isPresent();

        Brand existingBrand = optionalBrand.get();
        existingBrand.setName("Samsung Electronics");

        Brand updatedBrand = repo.save(existingBrand);

        assertThat(updatedBrand.getName()).isEqualTo("Samsung Electronics");
    }

    @Test
    void testDeleteBrand() {
        Brand brand = new Brand(3L);
        repo.deleteById(brand.getId());

        Optional<Brand> deletedBrand = repo.findById(brand.getId());

        assertThat(deletedBrand).isEmpty();
    }
}
