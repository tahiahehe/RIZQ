package com.rizq.controller;

import com.rizq.model.*;
import com.rizq.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class DonorController {

    private final DonationService donationService;
    private final UserService userService;

    public DonorController(DonationService donationService, UserService userService) {
        this.donationService = donationService;
        this.userService = userService;
    }

    @GetMapping("/donate")
    public String donateForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "donate";
    }

    @PostMapping("/donate")
    public String submitDonation(@Valid @ModelAttribute("donation") Donation donation,
                                 BindingResult result,
                                 @AuthenticationPrincipal UserDetails principal,
                                 RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "donate";
        }
        User donor = userService.findByEmail(principal.getUsername());
        donationService.create(donation, donor);
        redirect.addFlashAttribute("success", "Thank you! Your donation is now listed.");
        return "redirect:/donor/dashboard";
    }

    @GetMapping("/donor/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails principal, Model model) {
        User donor = userService.findByEmail(principal.getUsername());
        var myDonations = donationService.byDonor(donor);

        model.addAttribute("user", donor);
        model.addAttribute("donations", myDonations);
        model.addAttribute("totalCount", myDonations.size());
        model.addAttribute("servings", myDonations.stream().mapToInt(Donation::getServings).sum());
        model.addAttribute("deliveredCount",
                myDonations.stream().filter(d -> d.getStatus() == DonationStatus.DELIVERED).count());
        return "dashboard/donor";
    }

    @PostMapping("/donor/donations/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails principal,
                         RedirectAttributes redirect) {
        Donation d = donationService.findById(id);
        User donor = userService.findByEmail(principal.getUsername());
        if (d.getDonor() != null && d.getDonor().getId().equals(donor.getId())) {
            donationService.delete(id);
            redirect.addFlashAttribute("success", "Donation removed.");
        } else {
            redirect.addFlashAttribute("error", "You can only remove your own donations.");
        }
        return "redirect:/donor/dashboard";
    }
}
