package com.baza.digitalsecretary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    public static int parseStringToInteger(String str) {
        if (str.matches("\\d+")) {
            return Integer.parseInt(str);
        } else {
            return -1;
        }
    }

    private static String isIdValid(String id, String tableName) {
        List<String> ids = new ArrayList<>();
        try {
            var statement = DataManager.connection.prepareStatement("SELECT * FROM ?");
            statement.setString(1, tableName);

            var selectionSet = statement.executeQuery();
            while (selectionSet.next()) {
                ids.add(selectionSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String resultMessage;
        if (id.equals("")) {
            resultMessage = "Please, input event id first";
        } else if (DataManager.parseStringToInteger(id) == -1) {
            resultMessage = "Please, input a number";
        } else if (!(ids.contains(id))) {
            resultMessage = "Please, input an existing id";
        } else {
            resultMessage = "Valid id = " + id;
        }

        return resultMessage;
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

