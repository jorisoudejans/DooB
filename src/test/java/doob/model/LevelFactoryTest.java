package doob.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Class to test LevelFactory
 *
 * Created by hidde on 9/10/15.
 */
public class LevelFactoryTest {

    private LevelFactory levelFactory;
    @Spy
    private Canvas canvas;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuildLevelSimple() throws Exception {

        levelFactory = new LevelFactory(getClass().getResource("/level/simple.xml").getPath(), canvas);
        levelFactory.setPlayerImages(new Image[] { null, null, null });
        Level level = levelFactory.build();

        assertNotNull(level);
    }

    @Test
    public void testBuildLevelBall1() throws Exception {

        levelFactory = new LevelFactory(getClass().getResource("/level/ball1.xml").getPath(), canvas);
        levelFactory.setPlayerImages(new Image[] { null, null, null });
        Level level = levelFactory.build();

        assertNotNull(level);
    }
}
