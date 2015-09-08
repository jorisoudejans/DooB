package doob.model;

import doob.FXTestCase;
import javafx.scene.canvas.GraphicsContext;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Created by hidde on 9/8/15.
 *
 * Tests interface Drawable.
 */
public class DrawableTest {

    @Mock protected Drawable drawable;

    /**
     * Sets up the drawable to be tested.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        doThrow(new IllegalStateException("Mocked exception")).when(drawable).draw(null);
    }

    /**
     * Test the draw function of a drawable. We can't mock or access GraphicsContext, so this is
     * still a problem.
     */
    @Test
    public void testDraw() {

        // we can't verify anything at the moment, this should be done in the subclassed testcases
    }

    /**
     * Call draw with a null GraphicsContext.
     */
    @Test(expected=IllegalStateException.class)
    public void testDrawNull() {
        drawable.draw(null);
    }

    /**
     * Tests the move action, called when the object's position should change.
     */
    @Test
    public void testMove() {
        drawable.move();
    }
}
