package doob.controller;

import doob.App;
import doob.FXTestCase;
import doob.JavaFXThreadingRule;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by hidde on 9/8/15.
 */

public class GameControllerTest extends ControllerTest {

    /**
     * Tests that our game controller exists in file.
     */
    @Test
    public void testBasic() {
        GameController gameController = (GameController)getController("/fxml/game.fxml");
        assertNotNull(gameController);
    }

    /**
     * Tests whether controller is initalized properly.
     */
    @Test
    public void testInitialize() {
        GameController gameController = (GameController)getController("/fxml/game.fxml");
        assertNotNull(gameController.getLevel());
    }
}
