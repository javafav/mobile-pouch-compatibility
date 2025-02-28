package com.sastaybrands.mobiles.service;

import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.repo.MobileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MobileService {
    @Autowired
    private MobileRepository mobileRepository;



    public void saveMobile(Mobile mobile) {
        mobileRepository.save(mobile);
    }

    public List<Mobile> getAllMobiles() {
        return mobileRepository.findAll();
    }

    public List<Mobile> getMobilesByIds(List<Long> ids) {
        return mobileRepository.findAllById(ids);
    }



}
