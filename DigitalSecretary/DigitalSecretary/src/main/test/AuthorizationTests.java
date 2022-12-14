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
public class AuthorizationTests extends ApplicationTest {

    @BeforeEach
    public void setUpClass() throws Exception {
        ApplicationTest.launch(DigitalSecretaryApp.class);
        //Thread.sleep(5000);
    }

    /*public <T extends Node>  T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }*/


    /*@Override
    public void start(Stage stage) throws IOException {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(DigitalSecretaryApp.class.getResource("authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        primaryStage.setTitle("Digital Secretary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        //release(new KeyCode[]{});
        //release(new MouseButton[]{});
    }

    @Test
    public void successLogInTest() {
        String login = "admin";
        String password = "root";

        String expected = "Пользователь: " + login;

        clickOn("#LogInField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#SignInButton");


        FxAssert.verifyThat("#UserText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void loginNotRegisteredLogInTest() {
        String login = "admin1234";
        String password = "root";

        String expected = "Логин еще не зарегистрирован";

        clickOn("#LogInField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#SignInButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void invalidPasswordLogInTest() {
        String login = "admin";
        String password = "root123";

        String expected = "Неверный пароль";

        clickOn("#LogInField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#SignInButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void emptyPasswordLogInTest() {
        String login = "admin";
        String password = "";

        String expected = "Пароль не введен";

        clickOn("#LogInField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#SignInButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void emptyLoginLogInTest() {
        String login = "";
        String password = "root";

        String expected = "Логин не введен";

        clickOn("#LogInField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#SignInButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

/*    @Test
    public void emptyLoginRegistrationTest() {
        String login = "";
        String password = "root";

        String expected = "Логин должен содержать хотя бы один символ";

        clickOn("#LogInField").write(login);
        clickOn("#PasswordField").write(password);
        clickOn("#SignInButton");

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }*/


}
