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
import com.sastaybrands.mobiles.exception.MobileNotFoundException;
import com.sastaybrands.mobiles.repo.MobileRepository;

@Service
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

		Mobile mobileByName = repo.findByName(name);

		if (isCreatingNew) {
			if (mobileByName != null) {
				return "DuplicateName";
			} else {
				Mobile mobileByModel = repo.findByModel(model);
				if (mobileByModel != null) {
					return "DuplicateModel";
				}
			}
		} else {
			if (mobileByName != null && mobileByName.getId() != id) {
				return "DuplicateName";
			}

			Mobile mobileByModel = repo.findByModel(model);
			if (mobileByModel != null && mobileByModel.getId() != id) {
				return "DuplicateModel";
			}

		}

		return "OK";
	}
}
