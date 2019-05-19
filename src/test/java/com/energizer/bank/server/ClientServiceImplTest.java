package com.energizer.bank.server;

import org.junit.Before;
import org.junit.Test;

public class ClientServiceImplTest {

    @Mock
    private AccountService accountService;

    private ClientServiceImpl clientService;

    @Before
    public void setUp() throws Exception {
        Mockito.init(this);
        clientService = new ClientServiceImpl(accountService);
    }

    @Test
    public void save() {
    }

    @Test
    public void getByEmail() {
    }

    @Test
    public void update() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void getAvailableMoney() {
    }

    @Test
    public void getDebt() {
    }
}