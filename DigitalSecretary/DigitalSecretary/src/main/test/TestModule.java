import com.baza.digitalsecretary.DataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestModule {

    @Test
    public void justTest() {
        Assertions.assertEquals("success", DataManager.CheckAuthorization("admin", "root"));
    }

}
