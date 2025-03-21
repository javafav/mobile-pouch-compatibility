package com.sastaybrands.mobiles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sastaybrands.mobiles.service.MobileService;

@RestController
public class MobileRestController {

	@Autowired
	private MobileService mobileService;
	

	@PostMapping("/mobiles/check_unique")
	public String checkUnique(@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "name") String name, @RequestParam(name = "model") String model) {
		return mobileService.checkUnique(id, name, model);
	}



}
