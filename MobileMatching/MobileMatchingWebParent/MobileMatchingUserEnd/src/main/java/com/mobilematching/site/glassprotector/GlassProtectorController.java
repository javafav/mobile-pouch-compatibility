package com.mobilematching.site.glassprotector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mobilematching.common.entity.GlassProtector;

@Controller
public class GlassProtectorController {

	@Autowired
	private GlassProtectorService service;
	
	
	   @GetMapping("/protectors/{mobile}")
	    public String getComptible(Model model, @PathVariable String mobile) {
	      List<GlassProtector> listProtectors = service.findAll(mobile);
		 // model.addAttribute("listProtectors", listProtectors); 
	     listProtectors.forEach(pro -> System.out.println(pro.getPrimaryModel()));
	      return "result";
	    }
}
