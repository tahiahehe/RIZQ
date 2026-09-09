package com.rizq.controller;

import com.rizq.model.*;
import com.rizq.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final DonationService donationService;
    private final NgoService ngoService;

    public AdminController(UserService userService, DonationService donationService, NgoService ngoService) {
        this.userService = userService;
        this.donationService = donationService;
        this.ngoService = ngoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalDonors", userService.countByRole(Role.DONOR));
        model.addAttribute("totalNgoUsers", userService.countByRole(Role.NGO));
        model.addAttribute("totalDonations", donationService.totalDonations());
        model.addAttribute("totalServings", donationService.totalServings());
        model.addAttribute("availableCount", donationService.countByStatus(DonationStatus.AVAILABLE));
        model.addAttribute("claimedCount", donationService.countByStatus(DonationStatus.CLAIMED));
        model.addAttribute("deliveredCount", donationService.countByStatus(DonationStatus.DELIVERED));
        model.addAttribute("donations", donationService.findAll());
        return "dashboard/admin";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", Role.values());
        return "dashboard/admin-users";
    }

    @PostMapping("/users/{id}/role")
    public String changeRole(@PathVariable Long id, @RequestParam Role role, RedirectAttributes redirect) {
        userService.changeRole(id, role);
        redirect.addFlashAttribute("success", "Role updated.");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/enabled")
    public String toggleEnabled(@PathVariable Long id, @RequestParam boolean enabled, RedirectAttributes redirect) {
        userService.setEnabled(id, enabled);
        redirect.addFlashAttribute("success", enabled ? "Account enabled." : "Account suspended.");
        return "redirect:/admin/users";
    }

    @GetMapping("/ngos")
    public String ngos(Model model) {
        model.addAttribute("ngos", ngoService.findAll());
        model.addAttribute("ngo", new Ngo());
        return "dashboard/admin-ngos";
    }

    @PostMapping("/ngos")
    public String saveNgo(@ModelAttribute Ngo ngo, RedirectAttributes redirect) {
        ngoService.save(ngo);
        redirect.addFlashAttribute("success", "NGO saved.");
        return "redirect:/admin/ngos";
    }

    @PostMapping("/ngos/{id}/delete")
    public String deleteNgo(@PathVariable Long id, RedirectAttributes redirect) {
        ngoService.delete(id);
        redirect.addFlashAttribute("success", "NGO removed.");
        return "redirect:/admin/ngos";
    }

    @PostMapping("/donations/{id}/delete")
    public String deleteDonation(@PathVariable Long id, RedirectAttributes redirect) {
        donationService.delete(id);
        redirect.addFlashAttribute("success", "Donation removed.");
        return "redirect:/admin/dashboard";
    }
}
