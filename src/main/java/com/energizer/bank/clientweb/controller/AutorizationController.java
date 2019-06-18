package com.energizer.bank.clientweb.controller;

import com.energizer.bank.clientweb.Autorization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AutorizationController {
//    @RequestMapping(value = "/autorization")
//    public ModelAndView AutorizationPage() throws IOException {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("autorization");
//        return mv;
//    }


    @GetMapping("/autorization")
    public String greetingForm(Model model) {

        model.addAttribute("autorization", new Autorization());

        return "autorization";
    }

    @PostMapping("/autorization")
    public String greetingSubmit(@ModelAttribute Autorization autorization, Model model) {
        model.addAttribute("autorization", autorization);

            return "result";   // тут надо переправить на страницу с аккаунтом
    }


}
