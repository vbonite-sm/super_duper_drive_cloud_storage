package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupPage() {
        return "signup";
    }

    @PostMapping
    public String signUpUserAccount(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String signupError = null;

        if(!userService.isUserAvailable(user.getUsername())) {
            signupError = "Username already exists";
        }

        if(signupError == null) {
            int newRecords = userService.createUser(user);
            if(newRecords < 0) {
                signupError = "Error signing up. Try again later";
            }
        }

        if (signupError == null) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }
}
