package doob;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import doob.controller.LevelController;
import doob.controller.MenuController;
import doob.game.SinglePlayerGame;
import doob.model.level.Level;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.awt.*;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

/**
 * Created by hidde on 9/8/15.
 *
 * Cases with some simple game smoke tests (launching the game)
 */
public class AppSmokeTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private FXMLLoader fxmlLoader;

    @BeforeClass
    public static void setUpClass() throws Exception {
        assumeTrue(System.getenv("TRAVIS") == null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                App.launch(App.class);
            }
        }).start();
        Thread.sleep(200);
    }

    @Before
    public void setUp() throws Exception {
        fxmlLoader = new FXMLLoader();
    }

    @Test
    public void testLoadMenu() throws Exception {

        System.out.println("Smoking...");

        Pane p = fxmlLoader.load(App.class.getResource("/FXML/Menu.fxml").openStream());
        MenuController menuController = (MenuController) fxmlLoader.getController();

        assertTrue(menuController instanceof MenuController);
    }

    @Test
    public void testSinglePlayerGame() throws Exception{

        Pane p = fxmlLoader.load(App.class.getResource("/FXML/Menu.fxml").openStream());
        MenuController menuController = (MenuController) fxmlLoader.getController();
        menuController.playSinglePlayer();

        Thread.sleep(500);

        FXMLLoader gameLoader = new FXMLLoader();
        AnchorPane pane = gameLoader.load(App.class.getResource("/FXML/SinglePlayerGame.fxml").openStream());
        SinglePlayerGame gameController = (SinglePlayerGame) gameLoader.getController();

        Thread.sleep(500);

        Scene scene = App.getStage().getScene();

        FXRobot robot = FXRobotFactory.createRobot(scene);
        robot.keyPress(KeyCode.KP_RIGHT);
    }
}
