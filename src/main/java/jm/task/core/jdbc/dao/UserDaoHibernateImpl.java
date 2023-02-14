package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final static SessionFactory sF = Util.getSessionFactory();
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sF.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS myDb.users" +
                    " (id mediumint not null auto_increment, name VARCHAR(45), " +
                    "lastname VARCHAR(45), " +
                    "age INT, " + "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Created new table");
        } catch (HibernateException e) {
            System.out.println("Empty table" + e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sF.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS myDb.users").executeUpdate();
            transaction.commit();
            System.out.println("Users table drop");
        } catch (HibernateException e) {
            System.out.println("Table doesn't exist" + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sF.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("User doesn't save" + e);
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sF.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("User delete");
        } catch (HibernateException e) {
            System.out.println("Don't delete users by id" + e);
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = sF.openSession()) {
            transaction = session.beginTransaction();
            transaction.commit();
            return session.createQuery("from User", User.class).list();
        } catch (HibernateException e) {
            System.out.println("Get all users is failed" + e);
            transaction.rollback();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sF.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
            System.out.println("Table is cleaned");
        } catch (HibernateException e) {
            System.out.println("Table doesn't clean" + e);
            transaction.rollback();
        }
    }
}
