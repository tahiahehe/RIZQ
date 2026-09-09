package com.rizq.controller;

import com.rizq.model.*;
import com.rizq.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ngo")
public class NgoController {

    private final DonationService donationService;
    private final UserService userService;

    public NgoController(DonationService donationService, UserService userService) {
        this.donationService = donationService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails principal, Model model) {
        User ngo = userService.findByEmail(principal.getUsername());
        var claimed = donationService.byNgo(ngo);

        model.addAttribute("user", ngo);
        model.addAttribute("available", donationService.available());
        model.addAttribute("claimed", claimed);
        model.addAttribute("claimedCount", claimed.size());
        model.addAttribute("mealsReceived", claimed.stream().mapToInt(Donation::getServings).sum());
        return "dashboard/ngo";
    }

    @PostMapping("/donations/{id}/claim")
    public String claim(@PathVariable Long id,
                        @AuthenticationPrincipal UserDetails principal,
                        RedirectAttributes redirect) {
        try {
            donationService.claim(id, userService.findByEmail(principal.getUsername()));
            redirect.addFlashAttribute("success", "Donation claimed. Please arrange pickup.");
        } catch (IllegalStateException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/ngo/dashboard";
    }

    @PostMapping("/donations/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam DonationStatus status,
                               RedirectAttributes redirect) {
        donationService.updateStatus(id, status);
        redirect.addFlashAttribute("success", "Status updated to " + status.getLabel() + ".");
        return "redirect:/ngo/dashboard";
    }
}
