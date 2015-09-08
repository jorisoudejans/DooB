package doob.model;

import javafx.scene.canvas.GraphicsContext;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by hidde on 9/8/15.
 */
public class DrawableTest {

    @Mock protected Drawable drawable;
    @Mock protected GraphicsContext graphicsContextMock;

    /**
     * Sets up the drawable to be tested.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test the draw function of a drawable.
     */
    @Test
    public void testDraw() {
        drawable.draw(graphicsContextMock);

        // we can't verify anything at the moment, this should be done in the subclassed testcases
    }

    /**
     * Call draw with a null GraphicsContext
     */
    @Test(expected=IllegalStateException.class)
    public void testDrawNull() {
        drawable.draw(null);
    }
}
