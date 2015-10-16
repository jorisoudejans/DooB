package doob.level;

import doob.model.Level;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Class to test ObjectDrawer.
 */
public class ObjectDrawerTest {

    private GraphicsContext gc;
    private Canvas canvas;
    private Level level;

    @Before
    public void setUp() throws Exception {
        level = mock(Level.class);
    }

    @Test
    public void testDraw() throws Exception {

    }

    @Test
    public void testMove() throws Exception {

    }
}