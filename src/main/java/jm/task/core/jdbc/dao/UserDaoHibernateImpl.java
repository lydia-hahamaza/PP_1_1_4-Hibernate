package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction;
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS users " +
                            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45) NOT NULL, " +
                            "lastName VARCHAR(45) NOT NULL, age INT NOT NULL)"
            ).executeUpdate();
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            transaction.commit();

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        }
    }
}
