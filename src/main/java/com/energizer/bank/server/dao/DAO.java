package com.energizer.bank.server.dao;

import com.energizer.bank.server.entity.Client;

public interface DAO {
    void save(Client client);
    void delete (Client client);
    void update (Client client);
    Client findClientByEmail (String email);

}
