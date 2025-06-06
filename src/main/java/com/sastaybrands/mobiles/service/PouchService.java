package com.sastaybrands.mobiles.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.exception.MobileNotFoundException;
import com.sastaybrands.mobiles.exception.PouchNotFoundException;
import com.sastaybrands.mobiles.repo.MobileRepository;
import com.sastaybrands.mobiles.repo.PouchRepository;

@Service
public class PouchService {
   
	public static final int POUCH_PER_PAGE = 10;
	
	@Autowired private  PouchRepository pouchRepo;
	@Autowired private  MobileRepository mobileRepo;

    
    
    
    public List<Pouch> listAll() {
		return pouchRepo.findAll();
	}

    public Page<Pouch> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, POUCH_PER_PAGE, sort);

        if (keyword != null) {
            return pouchRepo.findAll(keyword, pageable);
        }

        return pouchRepo.findAll(pageable);
    }
    
  
    public Pouch save(Pouch pouch, List<Long> mobileIds) {
        if (mobileIds != null && !mobileIds.isEmpty()) {
            List<Mobile> mobiles = mobileRepo.findAllById(mobileIds);
            pouch.setCompatibleMobiles(mobiles);
        }
        return pouchRepo.save(pouch);
    }
    
    
	public Pouch get(Long id) throws PouchNotFoundException {
		try {

			return pouchRepo.findById(id).get();

		} catch (NoSuchElementException ex) {
			throw new PouchNotFoundException("Could not find the pouch with given (ID " + id + ")");
		}
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
