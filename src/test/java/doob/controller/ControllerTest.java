package doob.controller;

import doob.App;
import doob.JavaFXThreadingRule;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by hidde on 9/10/15.
 */
public class ControllerTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Tests controller can load.
     */
    //@Test
    //public void testBasic() {
    //    assertNotNull(getController("/fxml/menu.fxml"));
    //}

    protected Object getController(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(App.class.getResource(path).openStream());
        } catch (IOException e) {
            return null;
        }
        return fxmlLoader.getController();
    }

}
