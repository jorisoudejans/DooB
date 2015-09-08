package doob;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Custom TestCase to facilitate JavaFX to initialize for the tests
 *
 * Created by hidde on 9/8/15.
 */
public class FXTestCase {

    @BeforeClass
    public static void setUpFX() throws Exception {

        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start(); // now we can use basic graphics

        Thread.sleep(500);

    }

    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

}
