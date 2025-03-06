package com.sastaybrands.mobiles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sastaybrands.mobiles.service.BrandService;


@RestController
public class BrandRestController {

	@Autowired private BrandService service;
	
	@PostMapping("/brands/check_unique")
	public String checkUnique(@RequestParam(name = "id", required = false) Long id,@RequestParam(name = "name") String name) {
		return service.checkUnique(id, name);
	}
	

}
