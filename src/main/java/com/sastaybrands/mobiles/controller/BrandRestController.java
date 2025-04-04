package com.sastaybrands.mobiles.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sastaybrands.mobiles.entity.Brand;
import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.MobileDTO;
import com.sastaybrands.mobiles.exception.BrandNotFoundException;
import com.sastaybrands.mobiles.exception.BrandNotFoundRestException;
import com.sastaybrands.mobiles.service.BrandService;


@RestController
public class BrandRestController {

	@Autowired private BrandService service;
	
	@PostMapping("/brands/check_unique")
	public String checkUnique(@RequestParam(name = "id", required = false) Long id,@RequestParam(name = "name") String name) {
		return service.checkUnique(id, name);
	}
	
	@GetMapping("/brands/{id}/mobiles")
	public List<MobileDTO> mobileListByBrnad(@PathVariable("id") Long id) throws BrandNotFoundRestException {
		List<MobileDTO> mobileList = new ArrayList<>();
		try {

			Brand brand = service.get(id);
			List<Mobile> listMobiles = brand.getMobiles();

			for (Mobile mobile : listMobiles) {
				MobileDTO mobileDTO = new MobileDTO();
				mobileDTO.setId(mobile.getId());
				mobileDTO.setName(mobile.getName());
				mobileList.add(mobileDTO);

			}
			return mobileList;
		} catch (BrandNotFoundException e) {
			throw new BrandNotFoundRestException();
		}

	}
	

}
