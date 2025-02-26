package com.sastaybrands.mobiles.controller;

import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.service.MobileService;
import com.sastaybrands.mobiles.service.PouchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private MobileService mobileService;

    @Autowired
    private PouchService pouchService;

    @GetMapping
    public String home() {
        return "index";
    }

    @PostMapping("/search")
    public String searchMobile(@RequestParam String model, Model modelMap) {
        List<Pouch> pouches = pouchService.getMobileByModel(model); // Store the result properly

        if (pouches.size() > 0) {
            modelMap.addAttribute("mobile", model);
            modelMap.addAttribute("pouches", pouches);
            return "search-result";
        }

        modelMap.addAttribute("error", "Mobile model not found.");
        return "index";
    }

}

