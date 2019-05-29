package com.energizer.bank.server.dao;

import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.entity.Client;
import com.energizer.bank.server.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import java.util.List;

public class DAOImpl implements DAO {

    Transaction transaction = null;

    public void save(Client client) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save object
            session.save(client);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        transaction = null;
    }

    @Override
    public void delete(Client client) {
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
        transaction = null;

    }

    @Override
    public void update(Client client) {
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
        transaction = null;

    }


    /**
     * Return -1 if NoUser with this email
     */
    @Override
    public Client findClientByEmail(String email) {
        Client client;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Criteria userCriteria = session.createCriteria(Client.class);
            userCriteria.add(Restrictions.eq("email", email));
            client = (Client) userCriteria.uniqueResult();
            String hql = "FROM Account WHERE client_id = :paramId";
            Query query = session.createQuery(hql);
            query.setParameter("paramId", client.getId());
            List<Account> accounts = ((org.hibernate.query.Query) query).list();
            client.setAccounts(accounts);
            session.close();
        }
        return client;
    }

}
