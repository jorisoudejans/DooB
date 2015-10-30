package doob;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import doob.controller.LevelController;
import doob.controller.MenuController;
import doob.game.GameUI;
import doob.game.model.SinglePlayerGame;
import doob.model.Player;
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
import java.io.IOException;

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
        Thread.sleep(1500);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLoadMenu() throws Exception {
        MenuController menuController = loadMenu();
        assertTrue(menuController instanceof MenuController);
    }

    @Test
    public void testSinglePlayerGame() throws Exception{
        MenuController menuController = loadMenu();
        menuController.playSinglePlayer();
    }

    @Test
    public void testCoopGame() throws Exception{
        MenuController menuController = loadMenu();
        menuController.playCoopMode();
    }

    @Test
    public void testDuelGame() throws Exception{
        MenuController menuController = loadMenu();
        menuController.playDuelMode();
    }

    @Test
    public void testSurvivalGame() throws Exception{
        MenuController menuController = loadMenu();
        menuController.playSurvivalMode();
    }

    @Test
    public void testOptions() throws Exception{
        MenuController menuController = loadMenu();
        menuController.showOptions();
    }

    @Test
    public void testBuildLevel() throws Exception{
        MenuController menuController = loadMenu();
        menuController.buildLevel();
    }

    @Test
    public void testHighscoreSingle() throws Exception{
        MenuController menuController = loadMenu();
        menuController.showSinglePlayerHighscores();
    }

    @Test
    public void testHighscoreDuel() throws Exception{
        MenuController menuController = loadMenu();
        menuController.showDuelPlayerHighscores();
    }

    @Test
    public void testHighscoreCoop() throws Exception{
        MenuController menuController = loadMenu();
        menuController.showCoopPlayerHighscores();
    }

    @Test
    public void testHighscoreSurvival() throws Exception{
        MenuController menuController = loadMenu();
        menuController.showSurvivalHighscores();
    }

    @Test
    public void testMenuBack() throws Exception{
        MenuController menuController = loadMenu();
        menuController.backToMenu();
    }

    private MenuController loadMenu() throws IOException, InterruptedException {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.load(App.class.getResource("/FXML/Menu.fxml").openStream());
        return (MenuController)fxmlLoader.getController();
    }
}
