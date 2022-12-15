import com.baza.digitalsecretary.DigitalSecretaryApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.concurrent.TimeoutException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationTests extends ApplicationTest {

    @BeforeEach
    public void setUpClass() throws Exception {
        ApplicationTest.launch(DigitalSecretaryApp.class);
    }

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
    }

    @Test
    public void emptyLoginRegistrationTest() {
        String login = "";
        String password = "root";

        String expected = "Логин должен содержать хотя бы один символ";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void emptyPasswordRegistrationTest() {
        String login = "admin";
        String password = "";

        String expected = "Пароль должен содержать хотя бы один символ";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void invalidLengthOfPasswordRegistrationTest() {
        String login = "admin";
        String password = "root";

        String expected = "Пароль должен содержать от 6 до 20 символов";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void invalidLengthOfLoginRegistrationTest() {
        String login = "adminadminadminadminadminadminadminadmina";
        String password = "rootroot";

        String expected = "Логин должен содержать меньше 40 символов";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void PasswordWithSpaceRegistrationTest() {
        String login = "admin";
        String password = "root root";

        String expected = "Пароль содержит пробел";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void LoginWithSpaceRegistrationTest() {
        String login = "admin admin";
        String password = "rootroot";

        String expected = "Логин содержит пробел";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void LoginWithCyrillicRegistrationTest() {
        String login = "админ";
        String password = "rootroot";

        String expected = "Логин содержит кириллицу";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void PasswordWithCyrillicRegistrationTest() {
        String login = "admin";
        String password = "пароль";

        String expected = "Пароль содержит кириллицу";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void BusyLoginRegistrationTest() {
        String login = "admin";
        String password = "rootroot123";

        String expected = "Логин уже занят";

        clickOn("#GoToRegistrationButton");
        clickOn("#LoginField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#RegisterButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void BackButtonRegistrationTest() {
        clickOn("#GoToRegistrationButton");

        clickOn("#BackToAuthorizationButton");
        String expected = "";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }
}

