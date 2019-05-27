package com.energizer.bank.server;

import com.energizer.bank.server.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class ClientServiceImplTest {

    @Mock
    private AccountService accountService;

    private ClientServiceImpl clientService;

    private SimpleAccountService simpleAccountService;
    private Client client1;
    private List<Account> accounts1;
    private DepositAccount depositAccount1;
    private CreditAccount creditAccount1;

    @Before
    public void setUp() throws Exception {

        clientService = new ClientServiceImpl(accountService);
        simpleAccountService = new SimpleAccountService();

        //клиент1
        client1 = new Client();
        accounts1 = new ArrayList<>();
        depositAccount1 = new DepositAccount();
        depositAccount1.setDollars(500);
        accounts1.add(depositAccount1);
        creditAccount1 = new CreditAccount();
        creditAccount1.setDollars(300);
        accounts1.add(creditAccount1);
        client1.setAccounts(accounts1);
        client1.setEmail("client1@mail.mail");
        client1.setAge(20);
        client1.setName("Ivan");
        client1.setSurmame("Ivanov");
        client1.setGender(Gender.MALE);

    }

    @Test
    public void save() throws PersistException {

        clientService.save(client1);
        assertEquals(client1, clientService.getByEmail("client1@mail.mail"));

    }

    @Test
    public void getByEmail() throws PersistException {
        clientService.save(client1);
        assertEquals(client1.getEmail(), clientService.getByEmail("client1@mail.mail").getEmail());
    }

    @Test
    public void updateClient() throws PersistException, CloneNotSupportedException {
        clientService.save(client1);
        assertEquals("Ivanov", clientService.getByEmail("client1@mail.mail").getSurmame());
        assertEquals("Ivan", clientService.getByEmail("client1@mail.mail").getName());

        Client cloneClient1 = (Client) client1.clone();
        cloneClient1.setSurmame("updatedSurname");
        cloneClient1.setName("updatedName");

        clientService.update(cloneClient1);

        assertEquals("updatedSurname", clientService.getByEmail("client1@mail.mail").getSurmame());
        assertEquals("updatedName", clientService.getByEmail("client1@mail.mail").getName());

    }

    @Test
    public void remove() throws PersistException {
        clientService.save(client1);
        assertEquals(client1, clientService.getByEmail("client1@mail.mail"));
        clientService.remove(client1);
        assertEquals(null, clientService.getByEmail("client1@mail.mail"));


    }

    @Test
    public void getAvailableMoney() {
        Client client1 = new Client();


        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setDollars(200);
        List<Account> accounts = new ArrayList<>();

        accounts.add(depositAccount);
        client1.setAccounts(accounts);
        Map<Long, Integer> someMap = new HashMap<>();

        when(accountService.getAvailableMoney(accounts)).thenReturn(someMap);


        System.out.println("++++++" +clientService.getAvailableMoney(client1));
        verify(accountService).getAvailableMoney(accounts);
    }

    @Test
    public void getDebt() {


    }
}