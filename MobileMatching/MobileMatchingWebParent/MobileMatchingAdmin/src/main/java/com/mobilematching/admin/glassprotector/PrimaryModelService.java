package com.mobilematching.admin.glassprotector;
import com.mobilematching.entity.PrimaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimaryModelService {

    @Autowired
    private PrimaryModelRepository repo;

    public List<PrimaryModel> listAll() {
        return repo.findAll();
    }
}