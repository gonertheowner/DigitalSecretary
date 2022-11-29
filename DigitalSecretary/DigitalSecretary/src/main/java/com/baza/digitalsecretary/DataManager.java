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

    public static String isIdValid(String id, String tableName) throws SQLException {
        List<String> ids = new ArrayList<>();
        var selectionSet = selectAll(tableName);
        while (selectionSet.next()) {
            ids.add(selectionSet.getString(1));
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

    public static ResultSet selectAll(String tableName) {
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM " + tableName;
            resultSet =  connection.prepareStatement(query).executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Несуществующее имя таблицы: " + tableName);
        }
        return resultSet;
    }

    public static void delete(String id, String tableName) throws SQLException {
        var idColumnName = selectAll(tableName).getMetaData().getColumnName(1);
        var statement = connection.prepareStatement("DELETE FROM ? WHERE ? = ?");

        statement.setString(1, tableName);
        statement.setString(2, idColumnName);
        statement.setInt(3, Integer.parseInt(id));
        statement.execute();
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

