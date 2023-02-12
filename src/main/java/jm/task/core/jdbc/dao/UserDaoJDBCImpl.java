package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS myDb" +
                "(id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(45)," +
                "lastName VARCHAR(45)," +
                "age INT," + "PRIMARY KEY (id))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Created new table");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Empty table" + e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS myDb";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Users table drop");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Table doesn't exist" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO myDb(name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement pS = connection.prepareStatement(sql)) {
            pS.setString(1, name);
            pS.setString(2, lastName);
            pS.setByte(3, age);
            pS.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("User doesn't save" + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FORM myDb WHERE id = ?";
        try (PreparedStatement pS = Util.getConnection().prepareStatement(sql)) {
            pS.setLong(1, id);
            System.out.println("User delete");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Don't delete users by id" + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM myDb";
        List<User> listUsers = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            connection.commit();
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Get all users is failed" + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE myDb";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table is cleaned");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Table doesn't clean" + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
