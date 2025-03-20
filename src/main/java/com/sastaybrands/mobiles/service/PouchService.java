package com.sastaybrands.mobiles.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.repo.PouchRepository;

@Service
public class PouchService {
   
	public static final int POUCH_PER_PAGE = 10;
	
	@Autowired private  PouchRepository repo;

    
    
    
    public List<Pouch> listAll() {
		return repo.findAll();
	}

    public Page<Pouch> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, POUCH_PER_PAGE, sort);

        if (keyword != null) {
            return repo.findAll(keyword, pageable);
        }

        return repo.findAll(pageable);
    }

    
    
//    public List<Pouch> getCompatiblePouches(String mobileModel) {
//        return pouchRepository.findCompatiblePouchesByModel(mobileModel);
//    }
//    public void savePouch(Pouch pouch) {
//        pouchRepository.save(pouch);  // Saves pouch along with compatible mobiles
//    }
//
//    public List<Pouch> getAllPouches() {
//        return pouchRepository.findAll();
//    }
//
//    public List<Pouch> getMobileByModel(String model) {
//   return pouchRepository.findCompatiblePouchesByModel(model);
//    }
//    
//    public List<Pouch> getFilteredPouches(String material, String category, String brand, Double minPrice, Double maxPrice) {
//        Specification<Pouch> spec = Specification.where(PouchSpecification.hasMaterial(material))
//                .and(PouchSpecification.hasCategory(category))
//                .and(PouchSpecification.hasBrand(brand))
//                .and(PouchSpecification.hasPriceBetween(minPrice, maxPrice));
//
//        return pouchRepository.findAll(spec);
//    } 
    
}
