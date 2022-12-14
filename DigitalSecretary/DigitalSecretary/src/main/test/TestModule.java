import com.baza.digitalsecretary.AuthorizationController;
import com.baza.digitalsecretary.DataManager;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestModule {

    @Test
    public void successLogInTest() {
        String login = "admin";
        String password = "root";

        String expected = "success";
        String actual = DataManager.CheckAuthorization(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void loginNotRegisteredLogInTest() {
        String login = "admin1234";
        String password = "root";

        String expected = "Логин еще не зарегистрирован";
        String actual = DataManager.CheckAuthorization(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void invalidPasswordLogInTest() {
        String login = "admin";
        String password = "root123";

        String expected = "Неверный пароль";
        String actual = DataManager.CheckAuthorization(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void emptyPasswordLogInTest() {
        String login = "admin";
        String password = "";

        String expected = "Пароль не введен";
        String actual = DataManager.CheckAuthorization(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void emptyLoginLogInTest() {
        String login = "";
        String password = "root";

        String expected = "Логин не введен";
        String actual = DataManager.CheckAuthorization(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void emptyLoginRegistrationTest() {
        String login = "";
        String password = "root";

        String expected = "Логин должен содержать хотя бы один символ";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void emptyPasswordRegistrationTest() {
        String login = "admin";
        String password = "";

        String expected = "Пароль должен содержать хотя бы один символ";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void invalidLengthOfPasswordRegistrationTest() {
        String login = "admin";
        String password = "root";

        String expected = "Пароль должен содержать от 6 до 20 символов";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void invalidLengthOfLoginRegistrationTest() {
        String login = "adminadminadminadminadminadminadminadmina";
        String password = "rootroot";

        String expected = "Логин должен содержать меньше 40 символов";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void PasswordWithSpaceRegistrationTest() {
        String login = "admin";
        String password = "root root";

        String expected = "Пароль содержит пробел";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void LoginWithSpaceRegistrationTest() {
        String login = "admin admin";
        String password = "rootroot";

        String expected = "Логин содержит пробел";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void LoginWithCyrillicRegistrationTest() {
        String login = "админ";
        String password = "rootroot";

        String expected = "Логин содержит кириллицу";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void PasswordWithCyrillicRegistrationTest() {
        String login = "admin";
        String password = "пароль";

        String expected = "Пароль содержит кириллицу";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void PasswordWithoutLatinRegistrationTest() {
        String login = "admin";
        String password = "123456";

        String expected = "Пароль должен содержать латинские буквы";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void BusyLoginRegistrationTest() {
        String login = "admin";
        String password = "rootroot123";

        String expected = "Логин уже занят";
        String actual = DataManager.CheckRegistration(login, password);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void AddTodayEventTest() {
        String login = "admin";
        AuthorizationController.setLogin(login);
        LocalDate date = LocalDate.now();
        String category = "category";
        String title = "title";
        String description = "description";
        DataManager.AddEvent(date, category, title, description);
        ObservableList<String> todayEvents = DataManager.GetTodayEvents();
        todayEvents.forEach((event) -> {
            if (event.split(" ")[0] == date.toString() && event.split(" ")[1] == title) {
                Assertions.assertEquals(true, true);
            }
        });
    }
    @Test
    public void GetTodayEventsTest() {
        String login = "admin";
        String expected = LocalDate.now().toString();
        AuthorizationController.setLogin(login);
        ObservableList<String> todayEvents = DataManager.GetTodayEvents();
        todayEvents.forEach((event) -> {
            System.out.println(event.split(" ")[0]);
            String actual = event.split(" ")[0];
            Assertions.assertEquals(expected, actual);
        });
    }




}
