import com.baza.digitalsecretary.DigitalSecretaryApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTests extends ApplicationTest {

    @BeforeEach
    public void setUpClass() throws Exception {
        ApplicationTest.launch(DigitalSecretaryApp.class);
    }

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
    }

    @Test
    public void RightDateTest() {
        Date today = new Date();
        SimpleDateFormat formatToday = new SimpleDateFormat("'Сегодня: 'dd.MM.yyyy");
        String expected = formatToday.format(today);

        clickOn("#SkipButton");

        FxAssert.verifyThat("#TodayText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void SuccessAddEventTest() {
        String title = "title";
        String category = "category";
        String description = "description";

        clickOn("#SkipButton");
        clickOn("#GoToAddEventButton");
        clickOn("#TitleField").write(title);
        clickOn("#CategoryField").write(category);
        clickOn("#DescriptionField").write(description);
        clickOn("#AddEventButton");

        String expected = "Добавление прошло успешно";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void NotSuccessAddEventTest() {
        String category = "category";
        String description = "description";

        clickOn("#SkipButton");
        clickOn("#GoToAddEventButton");
        clickOn("#CategoryField").write(category);
        clickOn("#DescriptionField").write(description);
        clickOn("#AddEventButton");

        String expected = "Пожалуйста, введите название события";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void SuccessChangeEventTest() {
        String title = "changed_title";
        String category = "changed_category";
        String description = "changed_description";

        clickOn("#SkipButton");
        clickOn("#TodayEventListBox");
        //doubleClickOn(600, 340);
        clickOn("#GoToChangeEventButton");
        clickOn("#TitleField").eraseText(-1);
        clickOn("#TitleField").write(title);
        clickOn("#CategoryField").eraseText(-1);
        clickOn("#CategoryField").write(category);
        clickOn("#DescriptionField").eraseText(-1);
        clickOn("#DescriptionField").write(description);
        clickOn("#ChangeEventButton");
    }

    @Test
    public void NotSuccessChangeEventTest() {

        clickOn("#SkipButton");
        clickOn("#TodayEventListBox");
        clickOn("#GoToChangeEventButton");
        clickOn("#ChangeEventButton");

        String expected = "Ничего не изменено";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void EmptyTitleChangeEventTest() {
        String title = " ";

        clickOn("#SkipButton");
        clickOn("#TodayEventListBox");
        clickOn("#GoToChangeEventButton");
        clickOn("#TitleField").eraseText(-1);
        clickOn("#TitleField").write(title);
        clickOn("#TitleField").eraseText(1);
        clickOn("#ChangeEventButton");

        String expected = "Пожалуйста, введите название события";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void GoToAllEventTest() {
        clickOn("#SkipButton");
        clickOn("#TodayEventListBox");
        clickOn("#AllEventsButton");
    }

    @Test
    public void DeleteEventTest() {

        clickOn("#SkipButton");
        clickOn("#TodayEventListBox");
        clickOn("#DeleteEventButton");

        String expected = "Удаление прошло успешно";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void NotSuccessDeleteEventTest() {

        clickOn("#SkipButton");
        clickOn("#DeleteEventButton");

        String expected = "Ничего не выбрано";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void ChangeEventBackButtonTest() {

        clickOn("#SkipButton");
        clickOn("#TodayEventListBox");
        clickOn("#GoToChangeEventButton");
        clickOn("#BackToChooseEventButton");

        String expected = "";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void AllEventBackButtonTest() {

        clickOn("#SkipButton");
        clickOn("#AllEventsButton");
        clickOn("#BackToAppButton");

        String expected = "";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void AddEventBackButtonTest() {

        clickOn("#SkipButton");
        clickOn("#GoToAddEventButton");
        clickOn("#BackToAppButton");

        String expected = "";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void AllEventChangeTest() {
        String title = "Changed_title";
        String category = "changed_category";
        String description = "changed_description";

        clickOn("#SkipButton");
        clickOn("#AllEventsButton");
        clickOn("#EventsListBox");
        clickOn("#GoToChangeButton");

        clickOn("#TitleField").eraseText(-1);
        clickOn("#TitleField").write(title);
        clickOn("#CategoryField").eraseText(-1);
        clickOn("#CategoryField").write(category);
        clickOn("#DescriptionField").eraseText(-1);
        clickOn("#DescriptionField").write(description);
        clickOn("#ChangeEventButton");

        String expected = "";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void AllEventDeleteTest() {

        clickOn("#SkipButton");
        clickOn("#AllEventsButton");
        clickOn("#EventsListBox");
        clickOn("#deleteEventButton");

        String expected = "Удаление прошло успешно";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }

    @Test
    public void ExitTest() {

        clickOn("#SkipButton");
        clickOn("#ExitButton");

        String expected = "";

        FxAssert.verifyThat("#ErrorText", LabeledMatchers.hasText(expected));
    }


}

