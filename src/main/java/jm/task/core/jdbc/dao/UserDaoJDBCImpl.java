package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users" +
                "(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(45)," +
                "lastName VARCHAR(45)," +
                "age TINYINT(3))";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Empty table" + e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Table doesn't exist" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement pS = Util.getConnection().prepareStatement(sql)) {
            pS.setString(1, name);
            pS.setString(2, lastName);
            pS.setByte(3, age);
            pS.executeUpdate();
        } catch (SQLException e) {
            System.out.println("User doesn't save" + e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FORM users WHERE id = ?";
        try (PreparedStatement pS = Util.getConnection().prepareStatement(sql)) {
            pS.setLong(1, id);
            pS.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Don't delete users by id" + e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> listUsers = new ArrayList<>();
        try (ResultSet resultSet = Util.getConnection().createStatement().executeQuery(sql)) {
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Get all users is failed" + e);
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Table doesn't clean" + e);
        }
    }
}
