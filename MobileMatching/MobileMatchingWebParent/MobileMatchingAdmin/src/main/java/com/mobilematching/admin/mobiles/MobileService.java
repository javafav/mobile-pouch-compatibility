package com.mobilematching.admin.mobiles;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mobilematching.common.entity.Brand;
import com.mobilematching.common.entity.Mobile;
import com.mobilematching.exception.BrandNotFoundException;
import com.mobilematching.exception.MobileNotFoundException;

import jakarta.transaction.Transactional;



@Service
@Transactional
public class MobileService {

	public static final int MOBILES_PER_PAGE = 10;
	@Autowired
	private MobileRepository repo;

	public List<Mobile> listAll() {
		return repo.findAll();
	}

	public Page<Mobile> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, MOBILES_PER_PAGE, sort);

		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}

		return repo.findAll(pageable);
	}

	public Mobile get(Long id) throws MobileNotFoundException {
		try {

			return repo.findById(id).get();

		} catch (NoSuchElementException ex) {
			throw new MobileNotFoundException("Could not find the mobile with given (ID " + id + ")");
		}
	}

	public void delete(Long id) throws MobileNotFoundException {
		long count = repo.count(id);
		if (count == 0) {
			throw new MobileNotFoundException("Could not find the mobile with given (ID " + id + ")");
		}
		repo.deleteById(id);
	}

	public void saveMobile(Mobile mobile) {
		repo.save(mobile);
	}

	public String checkUnique(Long id, String name, String model) {
	    boolean isCreatingNew = (id == null || id == 0);

	    // Fetch both by name and model in a single step
	    Mobile mobileByName = repo.findByName(name);
	    Mobile mobileByModel = repo.findByModel(model);

	    if (isCreatingNew) {
	        if (mobileByName != null) {
	            return "DuplicateName";
	        }
	        if (mobileByModel != null) {
	            return "DuplicateModel";
	        }
	    } else {
	        if (mobileByName != null && !mobileByName.getId().equals(id)) {
	            return "DuplicateName";
	        }
	        if (mobileByModel != null && !mobileByModel.getId().equals(id)) {
	            return "DuplicateModel";
	        }
	    }

	    return "OK";
	}
	
	  public List<Mobile> findMobilesByIds(List<Long> mobileIds) {
	        return repo.findAllById(mobileIds);
	    }
	  
	  public void updateMobileEnableStatus(Long mobileId, boolean status) throws BrandNotFoundException {
			try {
				Mobile mobile = repo.findById(mobileId).get();
				if (mobile != null) {
					repo.updateEnabledStatus(mobileId, status);
				}
			} catch (NoSuchElementException ex) {
				throw new BrandNotFoundException("Mobile not found with given ID " + mobileId);
			}

		}

}
