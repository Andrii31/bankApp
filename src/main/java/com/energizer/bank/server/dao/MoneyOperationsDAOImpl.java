package com.energizer.bank.server.dao;

import com.energizer.bank.server.AccountService;
import com.energizer.bank.server.SimpleAccountService;
import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MoneyOperationsDAOImpl implements MoneyOperationsDAO {
    @Override
    public void withdraw(int dollars, Account account) {

        AccountService simpleAccountService = new SimpleAccountService();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            simpleAccountService.withdraw(dollars, account);
            session.update(account.getClient());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deposit(int dollars, Account account) {

        AccountService simpleAccountService = new SimpleAccountService();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            simpleAccountService.deposit(dollars, account);
            session.update(account.getClient());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void transfer(int dollars, Account from, Account to) {

        AccountService simpleAccountService = new SimpleAccountService();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            simpleAccountService.transfer(dollars, from, to);
            session.update(from.getClient());
            session.update(to.getClient());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
