package com.mobilematching.site.glassprotector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlassProtectorController {

	@Autowired
	private GlassProtectorRepository repo;
	
	   @GetMapping("/protectors")
	    public String getComptible(Model model) {
	        return "result";
	    }
}
