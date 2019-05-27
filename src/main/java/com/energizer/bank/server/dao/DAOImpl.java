package com.energizer.bank.server.dao;

import com.energizer.bank.server.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAOImpl implements DAO {

    public void save(Object o) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save object
            session.save(o);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
