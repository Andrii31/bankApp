package com.energizer.bank.server;

import java.util.HashMap;
import java.util.Map;

public interface ClientService {

    /**
     * Saves client in bank
     *
     * @throws PersistException if failed to persist
     */
    void save(Client client) throws PersistException;

    /**
     * Retrieves client by email
     *
     * @return client or null if such client does not exist
     * @throws PersistException if failed to retrieve
     */
    Client getByEmail(String email) throws PersistException;

    /**
     * Updates client in bank
     *
     * @throws PersistException if failed to persist
     */
    void update(Client client) throws PersistException;

    /**
     * Removes client from bank
     *
     * @throws PersistException if failed to remove
     */
    void remove(Client client) throws PersistException;

    /**
     * Returns id of account and available money. If there is no accounts or no money on accounts, returns empty map
     */
    Map<Long, Integer> getAvailableMoney(Client client);

    /**
     * Returns id of account and debt. If there is no credit accounts or no debts on accounts, returns empty map
     */
    Map<Long, Integer> getDebt(Client client);

    /**
     * Map for save clients
     */


}
