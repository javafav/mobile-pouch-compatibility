package com.sastaybrands.mobiles.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.repo.PouchRepository;
import com.sastaybrands.mobiles.repo.PouchSpecification;

@Service
public class PouchService {
    private final PouchRepository pouchRepository;

    public PouchService(PouchRepository pouchRepository) {
        this.pouchRepository = pouchRepository;
    }

    public List<Pouch> getCompatiblePouches(String mobileModel) {
        return pouchRepository.findCompatiblePouchesByModel(mobileModel);
    }
    public void savePouch(Pouch pouch) {
        pouchRepository.save(pouch);  // Saves pouch along with compatible mobiles
    }

    public List<Pouch> getAllPouches() {
        return pouchRepository.findAll();
    }

    public List<Pouch> getMobileByModel(String model) {
   return pouchRepository.findCompatiblePouchesByModel(model);
    }
    
    public List<Pouch> getFilteredPouches(String material, String category, String brand, Double minPrice, Double maxPrice) {
        Specification<Pouch> spec = Specification.where(PouchSpecification.hasMaterial(material))
                .and(PouchSpecification.hasCategory(category))
                .and(PouchSpecification.hasBrand(brand))
                .and(PouchSpecification.hasPriceBetween(minPrice, maxPrice));

        return pouchRepository.findAll(spec);
    } 
    
}
