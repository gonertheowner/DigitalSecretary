package com.baza.digitalsecretary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataManager {
    public static final Connection connection;
    private static ArrayList<Integer> allEventsIds;

    private static ArrayList<Integer> todayEventsIds;

    private static ArrayList<Integer> comingEventsIds;

    private static int SelectedEventId;

    private static LocalDate date;
    private static String title, category, description;

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

        allEventsIds = new ArrayList<>();
        todayEventsIds = new ArrayList<>();
        comingEventsIds = new ArrayList<>();
    }

    public static void SetEventInfo(LocalDate _date, String _title, String _category, String _description) {
        date = _date;
        title = _title;
        category = _category;
        description = _description;
    }
    public static int GetSelectedEventId() {
        return SelectedEventId;
    }

    public static void SetSelectedEventId(int id) {
        SelectedEventId = id;
    }

    public static int GetIdByNumberFromAllEvents(int number) {
        return allEventsIds.get(number);
    }

    public static int GetIdByNumberFromTodayEvents(int number) {
        return todayEventsIds.get(number);
    }

    public static int GetIdByNumberFromComingEvents(int number) {
        return comingEventsIds.get(number);
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
            if (title.equals("")) {
                return "Пожалуйста, введите название события";
            }

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

    public static String changeEvent(int id, LocalDate _date, String _title, String _category, String _description) {
        String message = "success";
        if (date.equals(_date) && title.equals(_title) && category.equals(_category) && description.equals(_description)) {
            message = "Ничего не изменено";
        } else if (_title.equals("")) {
            message = "Пожалуйста, введите название события";
        } else {
            try {
                var statement = connection.prepareStatement("UPDATE events SET date = ?, title = ?, category = ?, discription = ? WHERE id = ?");
                statement.setObject(1, _date);
                statement.setString(2, _title);
                statement.setString(3, _category);
                statement.setString(4, _description);
                statement.setInt(5, id);
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
            var statement = connection.prepareStatement("SELECT * FROM events WHERE login = ? ORDER BY date");
            statement.setString(1, AuthorizationController.getLogin());
            ResultSet resultSet = statement.executeQuery();
            allEventsIds.clear();
            while (resultSet.next()) {
                allEventsIds.add(resultSet.getInt(1));
                allEventsList.add(resultSet.getString(5) + " " + resultSet.getString(2) + " " + resultSet.getString(4) + " " + resultSet.getString(3));
            }
            return allEventsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void DeleteEvent(int id) {
        try {
            var statement = connection.prepareStatement("DELETE FROM events WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<String> GetTodayEvents() {
        try {
            ObservableList<String> allEventsList = FXCollections.observableArrayList();
            var statement = connection.prepareStatement("SELECT * FROM events WHERE login = ? AND date = ?");
            statement.setString(1, AuthorizationController.getLogin());
            statement.setObject(2, LocalDate.now());
            ResultSet resultSet = statement.executeQuery();
            todayEventsIds.clear();
            while (resultSet.next()) {
                todayEventsIds.add(resultSet.getInt(1));
                allEventsList.add(resultSet.getString(5) + " " + resultSet.getString(2) + " " + resultSet.getString(4) + " " + resultSet.getString(3));
            }
            return allEventsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<String> GetComingEvents() {
        try {
            ObservableList<String> allEventsList = FXCollections.observableArrayList();
            var statement = connection.prepareStatement("SELECT * FROM events WHERE login = ? AND date > ? ORDER BY date");
            statement.setString(1, AuthorizationController.getLogin());
            statement.setObject(2, LocalDate.now());
            ResultSet resultSet = statement.executeQuery();
            int count = 0;
            comingEventsIds.clear();
            while (resultSet.next()) {
                comingEventsIds.add(resultSet.getInt(1));
                allEventsList.add(resultSet.getString(5) + " " + resultSet.getString(2) + " " + resultSet.getString(4) + " " + resultSet.getString(3));
                count++;
                if (count >= 8) break;
            }
            return allEventsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}