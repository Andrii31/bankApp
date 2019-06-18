package com.energizer.bank.server;

import com.energizer.bank.server.dao.ClientDAO;
import com.energizer.bank.server.entity.Client;

import java.util.Map;

public class ClientServiceImpl implements ClientService {

    private final AccountService accountService;
    private ClientDAO clientDAO;

    public ClientServiceImpl(AccountService accountService, ClientDAO clientDAO) {
        this.accountService = accountService;
        this.clientDAO = clientDAO;
    }

    @Override
    public Client save(Client client) {


        return clientDAO.save(client);
    }

    @Override
    public Client getByEmail(String email) {

        return clientDAO.findClientByEmail(email);
    }

    @Override
    public void update(Client client) {
        clientDAO.update(client);

    }

    @Override
    public void remove(Client client) {
        clientDAO.delete(client);
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
