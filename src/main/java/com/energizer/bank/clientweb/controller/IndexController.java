package com.energizer.bank.clientweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public ModelAndView indexPage() throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

}
