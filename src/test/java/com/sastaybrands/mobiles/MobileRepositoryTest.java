package com.sastaybrands.mobiles;


import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.repo.MobileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class MobileRepositoryTest {

    @Autowired
    private MobileRepository mobileRepository;

    private Mobile mobile1;
    private Mobile mobile2;
    private Mobile duplicateMobile;

    @BeforeEach
    void setUp() {
        // Arrange: Create test mobiles
        mobile1 = new Mobile("Galaxy S21", "Samsung", "S21");
        mobile2 = new Mobile("iPhone 13", "Apple", "13");
        duplicateMobile = new Mobile("Galaxy S21", "Samsung", "S21");

        mobileRepository.save(mobile1);
        mobileRepository.save(mobile2);
        mobileRepository.save(duplicateMobile); // To test multiple results for the same model
    }

    @Test
    void testFindByModel_ShouldReturnList_WhenModelExists() {
        // Act
        List<Mobile> foundMobiles = mobileRepository.findByModel("S21");

        // Assert
        assertThat(foundMobiles).isNotEmpty();
        assertThat(foundMobiles).hasSize(2); // Since we added two "S21" models
        assertThat(foundMobiles).allMatch(mobile -> mobile.getModel().equals("S21"));
    }

    @Test
    void testFindByModel_ShouldReturnEmptyList_WhenModelDoesNotExist() {
        // Act
        List<Mobile> foundMobiles = mobileRepository.findByModel("NonExistentModel");

        // Assert
        assertThat(foundMobiles).isEmpty();
    }

    @Test
    void testFindAll_ShouldReturnAllMobiles() {
        // Act
        List<Mobile> mobiles = mobileRepository.findAll();

        // Assert
        assertThat(mobiles).hasSize(3);
        assertThat(mobiles).extracting(Mobile::getModel).containsExactlyInAnyOrder("S21", "13", "S21");
    }

    @Test
    void testDeleteById_ShouldRemoveMobile() {
        // Act
        mobileRepository.deleteById(mobile1.getId());
        List<Mobile> foundMobiles = mobileRepository.findByModel("S21");

        // Assert
        assertThat(foundMobiles).hasSize(1); // One "S21" model should still exist
    }
}
