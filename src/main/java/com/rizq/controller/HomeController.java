package com.rizq.controller;

import com.rizq.model.DonationStatus;
import com.rizq.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final DonationService donationService;
    private final NgoService ngoService;
    private final UserService userService;

    public HomeController(DonationService donationService, NgoService ngoService, UserService userService) {
        this.donationService = donationService;
        this.ngoService = ngoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featured", donationService.featured());
        model.addAttribute("totalDonations", donationService.totalDonations());
        model.addAttribute("totalServings", donationService.totalServings());
        model.addAttribute("totalNgos", ngoService.count());
        model.addAttribute("delivered", donationService.countByStatus(DonationStatus.DELIVERED));
        return "index";
    }

    @GetMapping("/browse")
    public String browse(@RequestParam(required = false) String q,
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) String city,
                         @RequestParam(required = false) DonationStatus status,
                         Model model) {
        model.addAttribute("donations", donationService.search(q, category, city, status));
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        model.addAttribute("city", city);
        model.addAttribute("status", status);
        return "browse";
    }

    @GetMapping("/ngos")
    public String ngos(Model model) {
        model.addAttribute("ngos", ngoService.findAll());
        return "ngos";
    }

    @GetMapping("/ngos/{id}")
    public String ngoProfile(@PathVariable Long id, Model model) {
        model.addAttribute("ngo", ngoService.findById(id));
        return "ngo-profile";
    }

    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/contact")
    public String contact() { return "contact"; }

    @GetMapping("/403")
    public String denied() { return "403"; }
}
