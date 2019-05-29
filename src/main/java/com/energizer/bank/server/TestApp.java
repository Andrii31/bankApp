package com.energizer.bank.server;

import com.energizer.bank.server.dao.ClientDAOImpl;
import com.energizer.bank.server.entity.*;

import java.util.ArrayList;
import java.util.List;

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

        AccountService accountService = new SimpleAccountService();
        ClientService clientService = new ClientServiceImpl(accountService);
        ClientDAOImpl daoImpl = new ClientDAOImpl();

/**
 * Create Client 1 with Accounts (Credit : 300, Deposit : 500) -->2 acc
 */
        //
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

/**
 * Create Client 2 with Account(Deposit : 0)
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
        // save with ClientService for tmp test
        clientService.save(client1);
        clientService.save(client2);

/**
 * save with BD via ClientDAOImpl
 */
        daoImpl.save(client1);
        daoImpl.save(client2);

        // get clients from ClientService
        Client gettedClient1 = clientService.getByEmail("client1@mail.mail");
        Client gettedClient2 = clientService.getByEmail("client2@mail.mail");

        // transfer 1300 dollars (500 from Deposit, 800 from credit money)
        CreditAccount GettedCreditAccount1 = findCreditAccount(gettedClient1);
        DepositAccount GettedDepositAccount2 = findDepositAccount(gettedClient2);
        accountService.transfer(1300, GettedCreditAccount1, GettedDepositAccount2);

/**
 * Update clients in DB
 */
        daoImpl.update(client1);
        daoImpl.update(client2);

/**
 * test find id by email
 */
        List<Account> testListAcc;
        Client testClient;
        try {
            testClient = daoImpl.findClientByEmail("client1@mail.mail");
            System.out.println("id testClient====  " + testClient.getId());
            System.out.println("name testClient====  " + testClient.getName());
            System.out.println("Gender testClient====  " + testClient.getGender());
            System.out.println("email testClient====  " + testClient.getEmail());
            testListAcc = testClient.getAccounts();
            System.out.println("Accounts in client id = " + testClient.getId() + " is " + testListAcc.size());
            System.out.println(testListAcc.get(0));
//            System.out.println(testListAcc.get(1));

        } catch (NullPointerException e) {
            System.out.println("Oupppssss...... No Client with this email");
        }

    }
}

