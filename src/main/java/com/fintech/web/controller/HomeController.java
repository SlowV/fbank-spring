package com.fintech.web.controller;

import com.fintech.domain.Account;
import com.fintech.domain.AccountInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("genders", AccountInformation.Gender.values());
        return "index";
    }
}
