package com.mobilematching.admin.pouches;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilematching.admin.brands.BrandService;
import com.mobilematching.admin.mobiles.MobileService;
import com.mobilematching.admin.util.FileUploadUtil;
import com.mobilematching.entity.Brand;
import com.mobilematching.entity.Mobile;
import com.mobilematching.entity.Pouch;
import com.mobilematching.exception.PouchNotFoundException;


@Controller

public class PouchController {

	@Autowired
	private PouchService pouchService;
	@Autowired
	private MobileService mobileService;
	@Autowired
	private BrandService brandService;

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

		return "pouches/pouches";
	}

	@GetMapping("/pouches/new")
	public String newMobile(Model model) {
		Pouch pouch = new Pouch();

		List<Brand> listBrands = brandService.listAll();
		pouch.setPrice(300);
		model.addAttribute("pouch", pouch);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Create New Pouch");

		return "pouches/pouch_form";
	}

	@PostMapping("/pouches/save")
	public String savePouch(Pouch pouch, RedirectAttributes redirectAttributes,
			@RequestParam("fileImage") MultipartFile multipartFile,
			@RequestParam(value = "compatibleMobiles", required = false) List<Long> mobileIds) throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			pouch.setImage(fileName);
			String uploadDir = "./pouch-photos/";
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			pouchService.save(pouch, mobileIds);

		} else {
			pouchService.save(pouch, mobileIds);
		}

		redirectAttributes.addFlashAttribute("message", "The pouch has been saved successfully.");
		return "redirect:/pouches";
	}

	@GetMapping("/pouches/edit/{id}")
	public String editBrand(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
		try {
			Pouch pouch = pouchService.get(id);
			List<Mobile> existingMobilesInDB = pouch.getCompatibleMobiles();
			List<Brand> listBrands = brandService.listAll();
			List<Mobile> listMobiles = mobileService.listAll(); // Get all mobiles

			model.addAttribute("pouch", pouch);
			model.addAttribute("listBrands", listBrands);
			
			model.addAttribute("existingMobilesInDB", existingMobilesInDB);
			model.addAttribute("listMobiles", listMobiles);

			model.addAttribute("pageTitle", "Edit Pouch (ID: " + id + ")");

			return "pouches/pouch_form";
		} catch (PouchNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/pouches";
		}
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
