package com.sastaybrands.mobiles.controller;

import com.sastaybrands.mobiles.entity.Mobile;
import com.sastaybrands.mobiles.entity.Pouch;
import com.sastaybrands.mobiles.service.MobileService;
import com.sastaybrands.mobiles.service.PouchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pouches")
public class PouchController {

    private final PouchService pouchService;
    private final MobileService mobileService;

    public PouchController(PouchService pouchService, MobileService mobileService) {
        this.pouchService = pouchService;
        this.mobileService = mobileService;
    }

    @GetMapping("/add")
    public String showAddPouchForm(Model model) {
        model.addAttribute("pouch", new Pouch());
        model.addAttribute("mobiles", mobileService.getAllMobiles());
        return "add-pouch";
    }

    @PostMapping("/save")
    public String savePouch(@ModelAttribute Pouch pouch, @RequestParam(required = false) List<Long> mobileIds) {
        if (mobileIds == null || mobileIds.isEmpty()) {
            return "redirect:/pouches/add?error=NoMobilesSelected";
        }

        List<Mobile> mobiles = mobileService.getMobilesByIds(mobileIds);
        pouch.setCompatibleMobiles(mobiles);
        pouchService.savePouch(pouch);  // This method was missing in your PouchService, so ensure it's implemented.

        return "redirect:/pouches/list";
    }

    @GetMapping("/list")
    public String listPouches(Model model) {
        model.addAttribute("pouches", pouchService.getCompatiblePouches("")); // Modified according to your service
        return "pouch-list";
    }
}
