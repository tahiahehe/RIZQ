package com.rizq.controller;

import com.rizq.model.Role;
import com.rizq.model.User;
import com.rizq.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) { this.userService = userService; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           @RequestParam(defaultValue = "DONOR") String accountType,
                           RedirectAttributes redirect,
                           Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            Role requested = "NGO".equalsIgnoreCase(accountType) ? Role.NGO : Role.DONOR;
            userService.register(user, requested);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
        redirect.addFlashAttribute("success", "Account created. Please sign in.");
        return "redirect:/login";
    }
}
