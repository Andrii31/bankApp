package com.energizer.bank.server;

import java.util.HashMap;
import java.util.Map;

public class ClientServiceImpl implements ClientService {

    private final AccountService accountService;
    private Map<String, Client> savedClients = new HashMap<>();

    public ClientServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void save(Client client) throws PersistException {
        savedClients.put(client.getEmail(), client);

    }

    @Override
    public Client getByEmail(String email) throws PersistException {

        return savedClients.get(email);
    }

    @Override
    public void update(Client client) throws PersistException {
        savedClients.replace(client.getEmail(), client);

    }

    @Override
    public void remove(Client client) throws PersistException {
        savedClients.remove(client.getEmail());
    }

    @Override
    public Map<Long, Integer> getAvailableMoney(Client client) {
        return accountService.getAvailableMoney(client.getAccounts());
    }

    @Override
    public Map<Long, Integer> getDebt(Client client) {
        return accountService.getDebts(client.getAccounts());
    }


}
