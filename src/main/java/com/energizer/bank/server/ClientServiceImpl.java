package com.energizer.bank.server;

import java.util.Map;

public class ClientServiceImpl implements ClientService{

    private final AccountService accountService;

    public ClientServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void save(Client client) throws PersistException {

    }

    @Override
    public Client getByEmail(String email) throws PersistException {
        return null;
    }

    @Override
    public void update(Client client) throws PersistException {

    }

    @Override
    public void remove(Client client) throws PersistException {

    }

    @Override
    public Map<Long, Integer> getAvailableMoney(Client client) {
        return null;
    }

    @Override
    public Map<Long, Integer> getDebt(Client client) {
        return null;
    }
}
