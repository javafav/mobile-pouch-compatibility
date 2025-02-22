package com.sastaybrands.mobiles.controller;

import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mobiles")
public class MobileController {
    @Autowired
    private MobileService mobileService;

    @GetMapping("/add")
    public String showAddMobileForm(Model model) {
        model.addAttribute("mobile", new Mobile());
        return "add-mobile";
    }

    @PostMapping("/save")
    public String saveMobile(@ModelAttribute Mobile mobile) {
        mobileService.saveMobile(mobile);
        return "redirect:/mobiles/list";
    }

    @GetMapping("/list")
    public String listMobiles(Model model) {
        model.addAttribute("mobiles", mobileService.getAllMobiles());
        return "mobile-list";
    }
}

