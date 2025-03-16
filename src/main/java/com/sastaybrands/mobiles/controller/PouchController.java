package com.sastaybrands.mobiles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.service.MobileService;
import com.sastaybrands.mobiles.service.PouchService;

@Controller

public class PouchController {

   @Autowired private PouchService pouchService;
   @Autowired  private  MobileService mobileService;

   
   @GetMapping("/pouches")
   public String getFilteredPouches(
           @RequestParam(required = false) String material,
           @RequestParam(required = false) String category,
           @RequestParam(required = false) String brand,
           @RequestParam(required = false) Double minPrice,
           @RequestParam(required = false) Double maxPrice,
           Model model) {

       List<Pouch> pouches = pouchService.getFilteredPouches(material, category, brand, minPrice, maxPrice);
       model.addAttribute("pouches", pouches);

       return "pouch-list";  // This corresponds to pouch-list.html in Thymeleaf
   }



//    @GetMapping("/list")
//    public String listPouches(Model model) {
//        model.addAttribute("pouches", pouchService.getCompatiblePouches("")); // Modified according to your service
//        return "pouch-list";
//    }
}
