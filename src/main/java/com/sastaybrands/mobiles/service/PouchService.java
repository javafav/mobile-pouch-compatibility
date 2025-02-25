package com.sastaybrands.mobiles.service;

import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.repo.PouchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
