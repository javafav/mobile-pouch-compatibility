package com.sastaybrands.mobiles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home() {
        return "index";
    }

//    @PostMapping("/search")
//    public String searchMobile(@RequestParam String model, Model modelMap) {
//        List<Pouch> pouches = pouchService.getMobileByModel(model); // Store the result properly
//
//        if (pouches.size() > 0) {
//            modelMap.addAttribute("mobile", model);
//            modelMap.addAttribute("pouches", pouches);
//            return "search-result";
//        }
//
//        modelMap.addAttribute("error", "Mobile model not found.");
//        return "index";
//    }

}

