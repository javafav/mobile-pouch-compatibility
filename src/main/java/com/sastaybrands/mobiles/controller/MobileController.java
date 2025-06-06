package com.sastaybrands.mobiles.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sastaybrands.mobiles.entity.Brand;
import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.exception.BrandNotFoundException;
import com.sastaybrands.mobiles.exception.MobileNotFoundException;
import com.sastaybrands.mobiles.service.BrandService;
import com.sastaybrands.mobiles.service.MobileService;
import com.sastaybrands.mobiles.util.FileUploadUtil;

@Controller
public class MobileController {

	@Autowired
	private MobileService mobileService;
	@Autowired
	private BrandService brandService;

	@GetMapping("/mobiles")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "name", "asc", null);
	}

	@GetMapping("/mobiles/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {
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

	@GetMapping("/mobiles/new")
	public String newMobile(Model model) {
		Mobile mobile = new Mobile();

		List<Brand> listBrands = brandService.listAll();

		model.addAttribute("mobile", mobile);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Create New Mobile");

		return "mobile_form";
	}

	@PostMapping("/mobiles/save")
	public String saveBrand(Mobile mobile, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			mobile.setPhoto(fileName);

			mobileService.saveMobile(mobile);
			String uploadDir = "./mobile-photos/";

			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			mobileService.saveMobile(mobile);
		}

		ra.addFlashAttribute("message", "The mobile has been saved successfully.");
		return "redirect:/mobiles";
	}

	@GetMapping("/mobiles/edit/{id}")
	public String editBrand(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
		try {
			Mobile mobile = mobileService.get(id);
			List<Brand> listBrands = brandService.listAll();

			model.addAttribute("mobile", mobile);
			model.addAttribute("listBrands", listBrands);

			model.addAttribute("pageTitle", "Edit Mobile (ID: " + id + ")");

			return "mobile_form";
		} catch (MobileNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/mobiles";
		}
	}
	
	
	   @GetMapping("/mobiles/delete/{id}")
	    public String deleteBrand(@PathVariable(name = "id") Long id,
	                              Model model,
	                              RedirectAttributes redirectAttributes) {
	        try {
	            Mobile mobile = mobileService.get(id);
	        	
	            String brandDir = "./mobile-photos/" + mobile.getPhoto();
	            FileUploadUtil.removeFile(brandDir);
	            mobileService.delete(id);

	            redirectAttributes.addFlashAttribute("message",
	                    "The mobile ID " + id + " has been deleted successfully");
	        } catch (MobileNotFoundException ex) {
	            redirectAttributes.addFlashAttribute("message", ex.getMessage());
	        }

	        return "redirect:/mobiles";
	    }

}
