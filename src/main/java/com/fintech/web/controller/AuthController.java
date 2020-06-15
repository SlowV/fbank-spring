package com.fintech.web.controller;

import com.fintech.domain.Account;
import com.fintech.service.AccountService;
import com.fintech.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @PostMapping("login")
    public String postLogin(@ModelAttribute("account") Account account, HttpSession session, final RedirectAttributes redirectAttributes) {
        Account result = authService.doLogin(account);
        if (result != null) {
            session.setAttribute("currentLogin", result);
        } else {
            redirectAttributes.addFlashAttribute("msgError", messageSource.getMessage("thông.báo.đăng.nhập.thất.bại", null, Locale.getDefault()));
            if (session.getAttribute("currentLogin") != null) session.removeAttribute("currentLogin");
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("account") Account account, final RedirectAttributes redirectAttributes) {
        Account result = authService.doRegister(account);
        if (result != null) {
            redirectAttributes.addFlashAttribute("msgRegisterSuccess", messageSource.getMessage("Đăng.ký.thành.công.với.tài.khoản:", null, Locale.getDefault()) + account.getUsername());
        } else {
            redirectAttributes.addFlashAttribute("msgRegisterFailed", messageSource.getMessage("Đăng.ký.thất.bại.vui.lòng.thử.lại!", null, Locale.getDefault()));
        }
        return "redirect:/";
    }
}
