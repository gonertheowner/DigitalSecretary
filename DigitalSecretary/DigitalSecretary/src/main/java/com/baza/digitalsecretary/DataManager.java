package com.baza.digitalsecretary;

import java.sql.*;


public class DataManager {
    public static final Connection connection;

    static
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(ConfProperties.getProperty("db.URL"), ConfProperties.getProperty("db.login"), ConfProperties.getProperty("db.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void selectAll(String tableName) {
        boolean flag;

         try {
             if (tableName == null) {
                 flag = false;
                 // OurClassException
             }
             var statement = connection.prepareStatement("SELECT * FROM ?");
             statement.setString(1, tableName);
         }
         catch (SQLException e) {
             // OurClassException
             throw new RuntimeException(e);
         }
    }

    public static boolean deleteUser(String tableName, int index) {
        boolean flag;
        if (tableName == null) {
            flag = false;
            // OurClassException
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setInt(1, index);
            flag = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }
    public static void addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Insert Into Users (login, password) VALUES (?,?)");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // OurClassException
            throw new RuntimeException(e);
        }
    }
    public static boolean updateUser(int index, User user) {
        boolean flag;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Users SET login=?, password=? WHERE ID=?");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, index);
            flag = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }
    public static boolean searchUser(User user) {
        boolean flag = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT login FROM Users WHERE EXISTS(SELECT login FROM Users where (login=? AND password=?))");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2,user.getPassword());
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                flag = true;
            }

        } catch (SQLException e) {
            // OurClassException
            throw new RuntimeException(e);
        }
        return flag;
    }
    public static boolean searchByLogin(User user) {
        boolean flag = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT login FROM Users WHERE EXISTS(SELECT login FROM Users where login=? )");
            preparedStatement.setString(1, user.getLogin());
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                flag = true;
            }

        } catch (SQLException e) {
            // OurClassException
            throw new RuntimeException(e);
        }
        return flag;
    }
}

