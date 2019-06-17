package com.energizer.bank.clientweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class RegistrationController {
    @RequestMapping(value = "/registration")
    public ModelAndView indexPage() throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registration");
        return mv;
    }
}
