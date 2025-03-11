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
import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.service.MobileService;

@Controller
public class MobileController {
	
	
	  @Autowired
	  private MobileService mobileService;


	    @GetMapping("/mobiles")
	    public String listFirstPage(Model model) {
	        return listByPage(1, model, "name", "asc", null);
	    }

	    @GetMapping("/mobiles/page/{pageNum}")
	    public String listByPage(
	            @PathVariable(name = "pageNum") int pageNum, Model model,
	            @Param("sortField") String sortField, @Param("sortDir") String sortDir,
	            @Param("keyword") String keyword
	    ) {
	        Page<Mobile> page = mobileService.listByPage(pageNum, sortField, sortDir, keyword);
	        List<Mobile> listMobiles = page.getContent();

	        long startCount = (pageNum - 1) * MobileService.MOBILES_PER_PAGE + 1;
	        long endCount = startCount + MobileService.MOBILES_PER_PAGE - 1;
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
	        model.addAttribute("listMobiles", listMobiles);
	     
	       

	        return "mobiles";
	    }
	
	
	
//    @Autowired
//    private MobileService mobileService;
//
//    @GetMapping("/add")
//    public String showAddMobileForm(Model model) {
//        model.addAttribute("mobile", new Mobile());
//        return "add-mobile";
//    }
//
//    @PostMapping("/save")
//    public String saveMobile(@ModelAttribute Mobile mobile) {
//        mobileService.saveMobile(mobile);
//        return "redirect:/mobiles/list";
//    }
//
//    @GetMapping("/list")
//    public String listMobiles(Model model) {
//        model.addAttribute("mobiles", mobileService.getAllMobiles());
//        return "mobile-list";
//    }
}

