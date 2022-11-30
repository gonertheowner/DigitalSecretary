package com.baza.digitalsecretary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public static void deleteEvent(String id) throws SQLException {
        var statement = connection.prepareStatement("DELETE FROM events WHERE id = ?");

        statement.setInt(1, Integer.parseInt(id));
        statement.execute();
    }

    public static void addingEvent(LocalDate date, String category, String title, String description) throws SQLException {
        var statement = connection.prepareStatement("INSERT INTO events (title, discription, category, date, login) VALUES(?, ?, ?, ?, ?)");
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, category);
        statement.setObject(4, date);
        statement.setString(5, AuthorizationController.getLogin());
        statement.execute();
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

    public static String AddUser(String login, String password) {
        String resultOfCheck = CheckRegistration(login, password);

        if (!resultOfCheck.equals("success")) return resultOfCheck;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users (login, password) VALUES (?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // OurClassException
            throw new RuntimeException(e);
        }

        return "success";
    }

    public static String CheckRegistration(String login, String password) {
        if (login.length() < 1) return "Логин должен содержать хотя бы один символ";

        if (password.length() < 1) return "Пароль должен содержать хотя бы один символ";

        if (password.length() < 6 || password.length() > 20) return "Пароль должен содержать от 6 до 20 символов";

        if (login.length() > 40) return "Логин должен содержать меньше 40 символов";

        if (password.contains(" ")) return "Пароль содержит пробел";

        if (login.contains(" ")) return "Логин содержит пробел";

        for (int i = 0; i < login.length(); i++) {
            if (Character.UnicodeBlock.of(login.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return "Логин содержит кириллицу";
            }
        }

        for (int i = 0; i < password.length(); i++) {
            if (Character.UnicodeBlock.of(password.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return "Пароль содержит кириллицу";
            }
        }

        String regex = "(\\d[a-z, A-Z]|[a-z, A-Z]\\d)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (!m.find()) return "Пароль должен содержать латинские буквы";

        User user = new User(login, password);
        if (DataManager.searchByLogin(user)) return "Логин уже занят";

        return "success";
    }

    public static String CheckAuthorization(String login, String password) {
        if (login.length() == 0) return "Логин не введен";

        if (password.length() == 0) return "Пароль не введен";

        User user = new User(login, password);

        if (!DataManager.searchByLogin(user)) return "Логин еще не зарегистрирован";

        if (!DataManager.searchUser(user)) return "Неверный пароль";

        return "success";
    }

    public static String AddEvent(LocalDate date, String category, String title, String description) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO events (title, discription, category, date, login) VALUES(?, ?, ?, ?, ?)");
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, category);
            statement.setObject(4, date);
            statement.setString(5, AuthorizationController.getLogin());
            statement.execute();
            return "success";
        } catch (SQLException e) {
            //????????????????????
            //????????????????????
            //????????????????????
            //????????????????????
            throw new RuntimeException(e);
        }
    }

    public static String changeEvent(String id, LocalDate date, String title, String category, String description) {
        String message = "success";
        if (title.equals("")) {
            message = "Пожалуйста, введите название события";
        } else {
            try {
                var statement = connection.prepareStatement("UPDATE events SET date = ?, title = ?, category = ?, discription = ? WHERE id = ?");
                statement.setObject(1, date);
                statement.setString(2, title);
                statement.setString(3, category);
                statement.setString(4, description);
                statement.setInt(5, Integer.parseInt(id));
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public static ObservableList<String> GetAllEventsList() {
        try {
            ObservableList<String> allEventsList = FXCollections.observableArrayList();
            var statement = connection.prepareStatement("SELECT * FROM events WHERE login = ?");
            statement.setString(1, AuthorizationController.getLogin());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allEventsList.add(resultSet.getString(1) + " " + resultSet.getString(5) + " " + resultSet.getString(2) + " " + resultSet.getString(4) + " " + resultSet.getString(3));
            }
            return allEventsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static String GetCheckEventIdInChoose(String id) {

        if (id.equals("")) return "Пожалуйста, введите id";

        if (parseStringToInteger(id) == -1) return "Пожалуйста, введите целое число";

        List<String> ids = new ArrayList<>();
        try {
            var selectionSet = selectAll("events");
            while (selectionSet.next()) {
                ids.add(selectionSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!ids.contains(id)) return "Пожалуйста, введите существующий id";

        return "success";
    }

    public static void DeleteEvent(String id) {
        try {
            var statement = connection.prepareStatement("DELETE FROM events WHERE id = ?");

            statement.setInt(1, Integer.parseInt(id));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}