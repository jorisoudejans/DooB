package doob;

import javafx.application.Application;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by hidde on 9/8/15.
 *
 * Cases with some simple game smoke tests (launching the game)
 */
public class AppSmokeTest {

    @Test
    public void testSmokeTest() throws Exception {

        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(App.class, new String[0]);


            }
        };
        t.start();
        Thread.sleep(4000);
    }
}
