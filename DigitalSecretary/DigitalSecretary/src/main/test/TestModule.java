import com.baza.digitalsecretary.DataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    public void notSuccessLogInTest() {
        String login = "admin1234";
        String password = "root";

        String expected = "Логин еще не зарегистрирован";
        String actual = DataManager.CheckAuthorization(login, password);

        Assertions.assertEquals(expected, actual);
    }

}
