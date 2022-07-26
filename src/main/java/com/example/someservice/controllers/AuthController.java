package com.example.someservice.controllers;

import com.example.someservice.entity.UserAccount;
import com.example.someservice.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/auth/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AccountServiceImpl accountService;

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLoginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("userAccount", new UserAccount());
            return "register_page";
        }
        return "redirect:/login_success";

    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseBody
    public String successLogin(@Valid @ModelAttribute("userAccount") UserAccount userAccount, BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register_page";
        }
        redirectAttributes.addAttribute("name", userAccount.getName());
        redirectAttributes.addAttribute("password", userAccount.getPassword());
        accountService.createAccount(userAccount);
        return "login_success";
    }
}
