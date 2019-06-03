package com.energizer.bank.server;

import com.energizer.bank.server.dao.ClientDAO;
import com.energizer.bank.server.dao.ClientDAOImpl;
import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.entity.Client;
import com.energizer.bank.server.entity.CreditAccount;
import com.energizer.bank.server.entity.DepositAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    @Mock
    private AccountService accountService;
    private ClientServiceImpl clientService;
    private Client client1;
    private Client client2;

    @Before
    public void setUp() throws PersistException {

        ClientDAO clientDAO = new ClientDAOImpl();

        accountService = new SimpleAccountService();
        clientService = new ClientServiceImpl(accountService, clientDAO);

        //Удаление конкретных клиентов из базы по email

        try {
            while (true) {
                clientService.remove(clientService.getByEmail("client1@mail.mail"));

                try {
                    while (true) {
                        clientService.remove(clientService.getByEmail("client2@mail.mail"));

                    }
                } catch (NullPointerException e) {
                }

            }
        } catch (NullPointerException e) {
        }


        //клиент1
        client1 = new Client();
        Account depositAccount1 = new DepositAccount();
        depositAccount1.setClient(client1);
        depositAccount1.setDollars(500);

        List<Account> accounts1 = new ArrayList<>();
        accounts1.add(depositAccount1);
        Account creditAccount1 = new CreditAccount();
        creditAccount1.setClient(client1);
        creditAccount1.setDollars(700);
        accounts1.add(creditAccount1);

        client1.setAccounts(accounts1);
        client1.setEmail("client1@mail.mail");
        client1.setAge(20);
        client1.setName("Ivan");
        client1.setSurmame("Ivanov");
        client1.setGender(Gender.MALE);

        //клиент2
        client2 = new Client();
        Account depositAccount2 = new DepositAccount();
        depositAccount2.setClient(client2);
        depositAccount2.setDollars(1500);
        List<Account> accounts2 = new ArrayList<>();
        accounts2.add(depositAccount2);
        Account creditAccount2 = new CreditAccount();
        creditAccount2.setClient(client2);
        creditAccount2.setDollars(300);
        accounts2.add(creditAccount2);

        client2.setAccounts(accounts2);
        client2.setEmail("client2@mail.mail");
        client2.setAge(35);
        client2.setName("Ivan2");
        client2.setSurmame("Ivanov2");
        client2.setGender(Gender.MALE);

    }

    @Test
    public void save() throws PersistException {

        clientService.save(client1);

        Client returnClient_1 = clientService.getByEmail("client1@mail.mail");

        assertEquals(client1.getName(), returnClient_1.getName());
        assertEquals(client1.getAge(), returnClient_1.getAge());
        assertEquals(client1.getSurmame(), returnClient_1.getSurmame());
        assertEquals(client1.getGender(), returnClient_1.getGender());
        assertEquals(client1.getEmail(), returnClient_1.getEmail());
        assertEquals(client1.getAccounts().size(), returnClient_1.getAccounts().size());
        if (client1.getAccounts().size() == returnClient_1.getAccounts().size()) {
            System.out.println(client1.getAccounts());
            System.out.println(returnClient_1.getAccounts());
            int accountsSize = client1.getAccounts().size();
            for (int index = 0; index < accountsSize; index++) {
                assertEquals(client1.getAccounts().get(index).getClass(), returnClient_1.getAccounts().get(index).getClass());
                assertEquals(client1.getAccounts().get(index).getDollars(), returnClient_1.getAccounts().get(index).getDollars());
            }
        }
    }


    @Test
    public void getByEmail() throws PersistException {
        clientService.save(client1);
        clientService.save(client2);
        assertEquals(client1.getEmail(), clientService.getByEmail("client1@mail.mail").getEmail());
        assertEquals(client2.getEmail(), clientService.getByEmail("client2@mail.mail").getEmail());
        try {
            assertEquals(client2.getEmail(), clientService.getByEmail("client3@mail.mail").getEmail());
        } catch (NullPointerException e) {
            System.out.println("NullpointerException - it is okay=)");
        }
    }

    @Test
    public void updateClient() throws PersistException {
        clientService.save(client1);
        assertEquals("Ivanov", clientService.getByEmail("client1@mail.mail").getSurmame());
        assertEquals("Ivan", clientService.getByEmail("client1@mail.mail").getName());

        Client clientForUpdate1 = clientService.getByEmail("client1@mail.mail");
        clientForUpdate1.setSurmame("updatedSurname");
        clientForUpdate1.setName("updatedName");

        clientService.update(clientForUpdate1);

        assertEquals("updatedSurname", clientService.getByEmail("client1@mail.mail").getSurmame());
        assertEquals("updatedName", clientService.getByEmail("client1@mail.mail").getName());

    }

    @Test
    public void remove() throws PersistException {

        try {
            while (true) {
                clientService.remove(clientService.getByEmail("client1@mail.mail"));
            }
        } catch (NullPointerException e) {
            System.out.println("It's okay");

        }

        clientService.save(client1);
        clientService.save(client2);

        assertEquals(clientService.getByEmail("client1@mail.mail").getEmail(), "client1@mail.mail");
        Client clientToRemove = clientService.getByEmail("client1@mail.mail");
        clientService.remove(clientToRemove);

        try {
            assertEquals(client1.getEmail(), clientService.getByEmail("client1@mail.mail").getEmail());
        } catch (NullPointerException e) {
            System.out.println("NullpointerException - it is okay=)");
        }
    }

    @Test
    public void getAvailableMoney() throws PersistException {

        clientService.save(client1);
        clientService.save(client2);

        Client clientForReturnMap1 = clientService.getByEmail("client1@mail.mail");
        Client clientForReturnMap2 = clientService.getByEmail("client2@mail.mail");

        Map<Long, Integer> availableMoneyClient1 = clientService.getAvailableMoney(clientForReturnMap1);
        Map<Long, Integer> availableMoneyClient2 = clientService.getAvailableMoney(clientForReturnMap2);


        //              notNULL                MAP               get VALUE by KEY                                     <->          VALUE
        assertEquals(java.util.Optional.of(availableMoneyClient1.get(clientForReturnMap1.getAccounts().get(0).getId())), java.util.Optional.of(500));
        assertEquals(java.util.Optional.of(availableMoneyClient1.get(clientForReturnMap1.getAccounts().get(1).getId())), java.util.Optional.of(1700));
        assertEquals(java.util.Optional.of(availableMoneyClient2.get(clientForReturnMap2.getAccounts().get(0).getId())), java.util.Optional.of(1500));
        assertEquals(java.util.Optional.of(availableMoneyClient2.get(clientForReturnMap2.getAccounts().get(1).getId())), java.util.Optional.of(1300));


//        Client client123 = new Client();
//
//        DepositAccount depositAccount = new DepositAccount();
//        depositAccount.setDollars(200);
//        List<Account> accounts123 = new ArrayList<>();
//
//        accounts123.add(depositAccount);
//        client123.setAccounts(accounts123);
//
//        Map<Long, Integer> someMap = new HashMap<>();
//
//        when(accountService.getAvailableMoney(accounts1)).thenReturn(someMap);
//
//        System.out.println("++++++" + clientService.getAvailableMoney(client123));
//        verify(accountService).getAvailableMoney(accounts1);

    }

    @Test
    public void getDebt() throws PersistException, NotValidMoneyInputException, NotEnoughMoneyException {

        clientService.save(client1);
        clientService.save(client2);

        Client clientForTransfer1 = clientService.getByEmail("client1@mail.mail");
        Client clientForTransfer2 = clientService.getByEmail("client2@mail.mail");

        Account depositAccClient1 = clientForTransfer1.getAccounts().get(0);

        Account creditAccClient2 = clientForTransfer2.getAccounts().get(1);

        accountService.transfer(900, creditAccClient2, depositAccClient1);

        clientService.update(clientForTransfer1);
        clientService.update(clientForTransfer2);

        Map<Long, Integer> debitClient1 = clientService.getDebt(clientService.getByEmail("client2@mail.mail"));

        assertEquals(java.util.Optional.of(debitClient1.get(clientService.getByEmail("client2@mail.mail").getAccounts().get(1).getId())), java.util.Optional.of(-600));

    }
}