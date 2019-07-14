package com.energizer.bank.clientweb.controller;

import com.energizer.bank.clientweb.Authorization;
import com.energizer.bank.server.dao.ClientDAO;
import com.energizer.bank.server.dao.ClientDAOImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class AuthorizationController {
    private ClientDAO clientDAO = new ClientDAOImpl();


    @GetMapping("/authorization")
    public String AuthorizationForm(Model model) {

        model.addAttribute("authorization", new Authorization());

        return "authorization";
    }

    @PostMapping("/authorization")
    public String authorizationSubmit(@ModelAttribute Authorization authorization, Model model) {
        model.addAttribute("authorization", authorization);

        if (Objects.isNull(clientDAO.findClientByEmail(authorization.getLogin())))
            return "user-not-exist";
            else
        if (!authorization.getPassword().equals(clientDAO.findClientByEmail(authorization.getLogin()).getPassword()))
              return "user-not-exist";
        else



            return "user-accepted";   // тут надо переправить на страницу с аккаунтом
        }
    }


