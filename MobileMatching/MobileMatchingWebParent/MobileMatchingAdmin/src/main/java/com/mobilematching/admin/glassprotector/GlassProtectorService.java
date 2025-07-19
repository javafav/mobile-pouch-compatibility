package com.mobilematching.admin.glassprotector;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mobilematching.admin.mobiles.MobileRepository;
import com.mobilematching.common.entity.GlassProtector;
import com.mobilematching.common.entity.Mobile;
import com.mobilematching.exception.GlassProtectorNotFoundException;

@Service
public class GlassProtectorService {

    public static final int GLASS_PROTECTORS_PER_PAGE = 10;

    @Autowired
    private GlassProtectorRepository glassProtectorRepo;

    @Autowired
    private MobileRepository mobileRepo;

    public List<GlassProtector> listAll() {
        return glassProtectorRepo.findAll();
    }

    public Page<GlassProtector> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, GLASS_PROTECTORS_PER_PAGE, sort);

        if (keyword != null && !keyword.isEmpty()) {
            return glassProtectorRepo.findAll(keyword, pageable);
        }

        return glassProtectorRepo.findAll(pageable);
    }

    public GlassProtector save(GlassProtector protector, List<Long> mobileIds) {
        if (mobileIds != null && !mobileIds.isEmpty()) {
            List<Mobile> mobiles = mobileRepo.findAllById(mobileIds);
            protector.setCompatibleMobiles(Set.copyOf(mobiles));
        }
        return glassProtectorRepo.save(protector);
    }

    public GlassProtector get(Long id) throws GlassProtectorNotFoundException {
        try {
            return glassProtectorRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new GlassProtectorNotFoundException("Could not find Glass Protector with ID " + id);
        }
    }

    public void delete(Long id) throws GlassProtectorNotFoundException {
        Long countById = glassProtectorRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new GlassProtectorNotFoundException("Could not find any Glass Protector with ID " + id);
        }

        glassProtectorRepo.deleteById(id);
    }
}
