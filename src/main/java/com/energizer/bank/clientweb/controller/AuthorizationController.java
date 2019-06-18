package com.energizer.bank.clientweb.controller;

import com.energizer.bank.clientweb.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorizationController {

    @GetMapping("/authorization")
    public String AuthorizationForm(Model model) {

        model.addAttribute("authorization", new Authorization());

        return "authorization";
    }

    @PostMapping("/authorization")
    public String authorizationSubmit(@ModelAttribute Authorization authorization, Model model) {
        model.addAttribute("authorization", authorization);

        return "result";   // тут надо переправить на страницу с аккаунтом
    }


}
