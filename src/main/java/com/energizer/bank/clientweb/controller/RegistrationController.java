package com.energizer.bank.clientweb.controller;

import com.energizer.bank.clientweb.Registration;
import com.energizer.bank.server.Gender;
import com.energizer.bank.server.entity.CreditAccount;
import com.energizer.bank.server.entity.DepositAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {


    private Registration registration;
    private String mail;

    @GetMapping("/registration1step")
    public String registration1stepForm(Model model) {

        model.addAttribute("registration1step", new Registration());

        return "registration1step";
    }

    @PostMapping("/registration1step")
    public String registration1stepSubmit(@ModelAttribute Registration reg, Model model) {
        model.addAttribute("registration1step", reg);
        if (reg.getTmp().equals(reg.getEmail())) return "user-already-exist";
        else {
            this.mail = reg.getEmail();
        }
        return "registration1stepOk";
    }

    @GetMapping("/registration2step")
    public String registration2stepForm(Model model) {

        model.addAttribute("registration2step", new Registration());

        return "registration2step";
    }

    @PostMapping("/registration2step")
    public String registration2stepSubmit(@ModelAttribute Registration reg, Model model) {
        model.addAttribute("autorization", reg);
        registration = new Registration();
        registration.setEmail(mail);
        registration.setAge(reg.getAge());

        if (reg.getGenderType()
                .toLowerCase()
                .equals("male")) {
            registration.setGender(Gender.MALE);
        } else {
            registration.setGender(Gender.FEMALE);
        }


        registration.setName(reg.getName());
        registration.setSurname(reg.getSurname());
        registration.setPassword(reg.getPassword());

        if (reg.getAccountType()
                .toLowerCase()
                .equals("da")) {
            registration.setAccounts(new DepositAccount());
        } else {
            registration.setAccounts(new CreditAccount());
        }

        //for test
        RegistrationPrint(registration);


        return "registration2stepOk";
    }

    //for test
    private void RegistrationPrint(Registration r) {
        System.out.println(r.getEmail());
        System.out.println(r.getPassword());
        System.out.println(r.getAge());
        System.out.println(r.getName());
        System.out.println(r.getSurname());
        System.out.println(r.getGender());
        System.out.println(r.getAccounts());
    }


}
