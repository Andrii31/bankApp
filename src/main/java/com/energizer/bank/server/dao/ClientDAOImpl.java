package com.energizer.bank.server.dao;

import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.entity.Client;
import com.energizer.bank.server.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {

    // private Transaction transaction;

    public Client save(Client client) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save client and set ID from returned PK
            client.setId((Integer) session.save(client));
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void delete(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /*
     * Return null if NoUser with this email
     */
    @Override
    public Client findClientByEmail(String email) {
        Client client = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cr = cb.createQuery(Client.class);
            Root root = cr.from(Client.class);
            cr.select(root);
            Query query = session.createQuery(cr);
            List<Client> results = query.getResultList();



            for (Client clt : results) {
                if (clt.getEmail().equals(email)) client = clt;
            }

            if (client==null) return null;



            /*
              Add accounts to List<Accounts> for client with specific parameters
             */
            String hql = "FROM Account WHERE client_id = :paramId";
            Query queryAccounts = session.createQuery(hql);
            queryAccounts.setParameter("paramId", client.getId());
            List<Account> accounts = ((org.hibernate.query.Query) queryAccounts).list();
            client.setAccounts(accounts);
        }
        return client;
    }

}
