package com.sastaybrands.mobiles.service;

import com.sastaybrands.mobiles.entity.Brand;
import com.sastaybrands.mobiles.exception.BrandNotFoundException;
import com.sastaybrands.mobiles.repo.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BrandService {


    public static final int BRANDS_PER_PAGE = 10;

    @Autowired
    private BrandRepository repo;


    public List<Brand> listAll() {
        return (List<Brand>) repo.findAll();
    }


    public Brand save(Brand brand) {
        return repo.save(brand);
    }

    public Page<Brand> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE, sort);

        if (keyword != null) {
            return repo.findAll(keyword, pageable);
        }

        return repo.findAll(pageable);
    }


    public Brand get(Long id) throws BrandNotFoundException {
        try {

            return repo.findById(id).get();

        } catch (NoSuchElementException ex) {
            throw new BrandNotFoundException("Could not find the brand with given (ID " + id + ")");
        }
    }


    public void delete(Long id) throws BrandNotFoundException {
        long count = repo.count(id);
        if (count == 0) {
            throw new BrandNotFoundException("Could not find the brand with given (ID " + id + ")");
        }
        repo.deleteById(id);
    }


    public String checkUnique(Long id, String name) {
        boolean isCreatingNew = (id == null || id == 0L);
        Brand brandByName = repo.findByName(name);
        if (isCreatingNew) {
            if (brandByName != null) {
                return "Duplicate";
            }
        } else if (brandByName != null && brandByName.getId() != id) {
            return "Duplicate";
        }

        return "OK";
    }
}
