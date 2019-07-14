package com.energizer.bank.clientweb.controller;

import com.energizer.bank.clientweb.Registration;
import com.energizer.bank.server.Gender;
import com.energizer.bank.server.dao.ClientDAO;
import com.energizer.bank.server.dao.ClientDAOImpl;
import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.entity.Client;
import com.energizer.bank.server.entity.CreditAccount;
import com.energizer.bank.server.entity.DepositAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class RegistrationController {

    private ClientDAO clientDAO = new ClientDAOImpl();

    @GetMapping("/registration")
    public String registrationForm(Model model) {

        model.addAttribute("registration", new Registration());

        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute Registration reg, Model model) {
        model.addAttribute("registration", reg);


        Client client = new Client();
        List<Account> accounts = new ArrayList<>();

        if (!Objects.isNull(clientDAO.findClientByEmail(reg.getEmail())))
            return "user-already-exist";
        else {

            client.setEmail(reg.getEmail());
            client.setAge(reg.getAge());

            if (reg.getGenderType()
                    .toLowerCase()
                    .equals("male")) {
                client.setGender(Gender.MALE);
            } else {
                client.setGender(Gender.FEMALE);
            }


            client.setName(reg.getName());
            client.setSurname(reg.getSurname());
            client.setPassword(reg.getPassword());

            if (reg.getAccountType().equals("da")) {
                Account depo = new DepositAccount();
                depo.setClient(client);
                accounts.add(depo);
            } else {
                Account credit  = new CreditAccount();
                credit.setClient(client);
                accounts.add(credit);
            }
            client.setAccounts(accounts);

            clientDAO.save(client);

            return "registrationOk";
        }
    }

}
