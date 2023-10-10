package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        return user.getUserId();
    }

    @GetMapping
    public String homePage(Authentication authentication, @ModelAttribute("uploadCredential") CredentialModel uploadCredential, Model model) {
        Integer id = getUserId(authentication);
        model.addAttribute("credentials", this.credentialService.getCredentialList(id));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping("addCredential")
    public String newCredentialRecord(Authentication authentication, @ModelAttribute("uploadCredential") CredentialModel uploadCredential, Model model) throws IOException {
        String username = authentication.getName();
        String password = uploadCredential.getPassword();
        String credentialUrl = uploadCredential.getCredentialUrl();
        String credentialId = uploadCredential.getCredentialId();
        SecureRandom rand = new SecureRandom();
        byte[] key = new byte[16];
        rand.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        if(credentialId.isEmpty()) {
            credentialService.addCredential(credentialUrl, username, uploadCredential.getCredentialUsername(), encodedKey, encryptedPassword);
        } else {
            Credential editCredential = getCredential(Integer.parseInt(credentialId));
            credentialService.updateCredential(editCredential.getCredentialid(), uploadCredential.getCredentialUsername(), credentialUrl, encodedKey, encryptedPassword);
        }
        User user = userService.getUser(username);
        model.addAttribute("credentials", credentialService.getCredentialList(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "redirect:/result?success";
    }

    @GetMapping(value = "/editCredential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.geCredential(credentialId);
    }

    @GetMapping(value = "/deleteCredential/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, @ModelAttribute("uploadCredential") CredentialModel uploadCredential, Model model) {
        credentialService.deleteCredential(credentialId);
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("credentials", credentialService.getCredentialList(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "redirect:/result?success";
    }
}
