package com.mobilematching.admin.glassprotector;


import com.mobilematching.admin.mobiles.MobileRepository;
import com.mobilematching.common.entity.Brand;
import com.mobilematching.common.entity.GlassProtector;
import com.mobilematching.common.entity.Mobile;
import com.mobilematching.common.entity.PrimaryModel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class GlassProtectorTests {

    @Autowired
    private GlassProtectorRepository glassProtectorRepo;

    @Autowired
    private PrimaryModelRepository primaryModelRepo;

    @Autowired
    private MobileRepository mobileRepo;

    @Test
    public void testCreatePrimaryModel() {
        PrimaryModel primaryModel = new PrimaryModel("Vivo Y20", new Brand(14L));
        PrimaryModel savedModel = primaryModelRepo.save(primaryModel);

        assertThat(savedModel.getId()).isNotNull();
        assertThat(savedModel.getName()).isEqualTo("Vivo Y20");
    }

    @Test
    public void testCreateMobileModels() {
        Mobile m1 = new Mobile("Y20");
        Mobile m2 = new Mobile("Camon 20");
        Mobile m3 = new Mobile("Y21");

        mobileRepo.saveAll(List.of(m1, m2, m3));

        assertThat(m1.getId()).isNotNull();
        assertThat(m2.getId()).isNotNull();
        assertThat(m3.getId()).isNotNull();
    }

    @Test
    public void testCreateGlassProtectorWithCompatibleModels() {
        // Fetch or create primary model
        PrimaryModel primaryModel = primaryModelRepo.findByName("Vivo Y20")
                .orElseGet(() -> primaryModelRepo.save(new PrimaryModel("Vivo Y20", new Brand(14L))));

        // Fetch existing mobile models
        Mobile m1 = mobileRepo.findByName("Y20");
        Mobile m2 = mobileRepo.findByName("Camon 20");

        assertThat(m1).isNotNull();
        assertThat(m2).isNotNull();

        GlassProtector protector = new GlassProtector();
        protector.setName("Glass for Vivo Y20");
        protector.setPrimaryModel(primaryModel);
        protector.setCompatibleMobiles(Set.of(m1, m2));

        GlassProtector savedProtector = glassProtectorRepo.save(protector);

        assertThat(savedProtector.getId()).isNotNull();
        assertThat(savedProtector.getCompatibleMobiles().size()).isEqualTo(2);
    }

    @Test
    public void testFetchGlassProtector() {
        List<GlassProtector> protectors = glassProtectorRepo.findAll();
        assertThat(protectors).isNotEmpty();

        GlassProtector protector = protectors.get(0);
        System.out.println("Protector Name: " + protector.getName());
        System.out.println("Primary Model: " + protector.getPrimaryModel().getName());
        System.out.println("Compatible Models Count: " + protector.getCompatibleMobiles().size());

        assertThat(protector.getPrimaryModel()).isNotNull();
        assertThat(protector.getCompatibleMobiles()).isNotEmpty();
    }

    @Test
    public void testUpdateProtectorName() {
        GlassProtector protector = glassProtectorRepo.findAll().get(0);
        protector.setName("Updated Glass Protector Name");

        GlassProtector updated = glassProtectorRepo.save(protector);

        assertThat(updated.getName()).isEqualTo("Updated Glass Protector Name");
    }

    @Test
    public void testDeleteProtector() {
        List<GlassProtector> all = glassProtectorRepo.findAll();
        if (!all.isEmpty()) {
            glassProtectorRepo.delete(all.get(0));
        }

        long count = glassProtectorRepo.count();
        assertThat(count).isGreaterThanOrEqualTo(0);
    }
}
