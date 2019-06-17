package com.energizer.bank.server;

import com.energizer.bank.server.dao.ClientDAO;
import com.energizer.bank.server.dao.ClientDAOImpl;
import com.energizer.bank.server.dao.MoneyOperationsDAO;
import com.energizer.bank.server.dao.MoneyOperationsDAOImpl;
import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.entity.Client;
import com.energizer.bank.server.entity.CreditAccount;
import com.energizer.bank.server.entity.DepositAccount;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TestApp {

    private static DepositAccount findDepositAccount(Client client) {
        Account account = null;
        for (Account acc : client.getAccounts()) {
            if (acc instanceof DepositAccount) account = acc;
        }
        return (DepositAccount) account;
    }

    private static CreditAccount findCreditAccount(Client client) {
        Account account = null;
        for (Account acc : client.getAccounts()) {
            if (acc instanceof CreditAccount) account = acc;
        }
        return (CreditAccount) account;
    }

    public static void main(String[] args) throws PersistException, NotValidMoneyInputException, NotEnoughMoneyException {
        SpringApplication.run(TestApp.class, args);
        AccountService accountService = new SimpleAccountService();
        ClientDAO clientDAO = new ClientDAOImpl();
        ClientService clientService = new ClientServiceImpl(accountService, clientDAO);

        MoneyOperationsDAO moneyOperationsDAO = new MoneyOperationsDAOImpl();



/*
  Create Client 1 with Accounts (Credit : 300, Deposit : 500) -->2 acc
 */
        Client client1 = new Client();
        List<Account> accounts1 = new ArrayList<>();
        Account depositAccount1 = new DepositAccount();
        depositAccount1.setClient(client1);
        depositAccount1.setDollars(500);
        accounts1.add(depositAccount1);
        Account creditAccount1 = new CreditAccount();
        creditAccount1.setClient(client1);
        creditAccount1.setDollars(300);
        accounts1.add(creditAccount1);
        client1.setAccounts(accounts1);
        client1.setEmail("client1@mail.mail");
        client1.setAge(20);
        client1.setName("Ivan");
        client1.setSurmame("Ivanov");
        client1.setGender(Gender.MALE);

/*
  Create Client 2 with Account(Deposit : 0)
 */
        Client client2 = new Client();
        List<Account> accounts2 = new ArrayList<>();
        Account depositAccount2 = new DepositAccount();
        depositAccount2.setClient(client2);
        depositAccount2.setDollars(0);
        accounts2.add(depositAccount2);
        client2.setAccounts(accounts2);
        client2.setEmail("client2@mail.mail");
        client2.setAge(25);
        client2.setName("Oksana");
        client2.setSurmame("Oksanovna");
        client2.setGender(Gender.FEMALE);

/*
  save clients in BD via ClientService
 */
        clientService.save(client1);
        System.out.println(client2.getId());
        System.out.println(clientService.save(client2).getId());


//        SpringApplication.run(TestApp.class, args);

        // Return clients from ClientService
        Client returnedFromDBClient_1 = clientService.getByEmail("client1@mail.mail");
        Client returnedFromDBClient_2 = clientService.getByEmail("client2@mail.mail");
//
//        // find some account_type in the Clients for transfer
        CreditAccount foundCreditAccount1 = findCreditAccount(returnedFromDBClient_1);
        DepositAccount foundDepositAccount1 = findDepositAccount(returnedFromDBClient_1);
        DepositAccount foundDepositAccount2 = findDepositAccount(returnedFromDBClient_2);
//
//
        moneyOperationsDAO.transfer(292, foundDepositAccount1, foundCreditAccount1);

//        // transfer 1300 dollars (500 from Deposit, 800 from credit money)
//        accountService.transfer(1300, foundCreditAccount1, foundDepositAccount2);
//
///*
//  Update clients in DB
// */
//        clientService.update(returnedFromDBClient_1);
//        clientService.update(returnedFromDBClient_2);


//        Map<Long, Integer> someMap = clientService.getAvailableMoney((clientService.getByEmail("client2@mail.mail")));
//
//        System.out.println(someMap);


    }
}

