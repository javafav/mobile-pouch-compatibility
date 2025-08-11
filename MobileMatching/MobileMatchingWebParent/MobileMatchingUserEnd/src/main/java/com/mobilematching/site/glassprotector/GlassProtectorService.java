package com.mobilematching.site.glassprotector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilematching.common.entity.GlassProtector;

@Service
public class GlassProtectorService {

	@Autowired
	private GlassProtectorRepository repo;
	
	public List<GlassProtector> findAll(String mobile){
	List<GlassProtector> findAll = (List<GlassProtector>) repo.findAll(mobile);
	return	findAll;
	}
}
