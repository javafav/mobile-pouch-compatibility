package com.sastaybrands.mobiles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sastaybrands.mobiles.entity.Brand;
import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.service.BrandService;
import com.sastaybrands.mobiles.service.MobileService;
import com.sastaybrands.mobiles.service.PouchService;

@Controller

public class PouchController {

   @Autowired private PouchService pouchService;
   @Autowired  private  MobileService mobileService;
   @Autowired  private  BrandService brandService;

   @GetMapping("/pouches")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "name", "asc", null);
	}

   
	@GetMapping("/pouches/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {
		
		Page<Pouch> page = pouchService.listByPage(pageNum, sortField, sortDir, keyword);
		List<Pouch> listPouches = page.getContent();

		long startCount = (pageNum - 1) * PouchService.POUCH_PER_PAGE + 1;
		long endCount = startCount + PouchService.POUCH_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("currentPage", pageNum);

		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);

		model.addAttribute("listPouches", listPouches);

		return "pouches";
	}

   
	@GetMapping("/pouches/new")
	public String newMobile(Model model) {
		Pouch pouch = new Pouch();

		List<Brand> listBrands = brandService.listAll();

		model.addAttribute("pouch", pouch);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Create New Pouch");

		return "pouch_form";
	}
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
//   @GetMapping("/pouches1")
//   public String getFilteredPouches(
//           @RequestParam(required = false) String material,
//           @RequestParam(required = false) String category,
//           @RequestParam(required = false) String brand,
//           @RequestParam(required = false) Double minPrice,
//           @RequestParam(required = false) Double maxPrice,
//           Model model) {
//
//       List<Pouch> pouches = pouchService.getFilteredPouches(material, category, brand, minPrice, maxPrice);
//       model.addAttribute("pouches", pouches);
//
//       return "pouches";  // This corresponds to pouch-list.html in Thymeleaf
//   }
//   
//   
   



//    @GetMapping("/list")
//    public String listPouches(Model model) {
//        model.addAttribute("pouches", pouchService.getCompatiblePouches("")); // Modified according to your service
//        return "pouch-list";
//    }
}
