package doob.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Level.
 */
public abstract class LevelTest {

    protected static final double CANVAS_DIMENSION = 1000;

    /**
     * Call extensions setUp.
     */
    @Before
    public void setUpTest() {
        setUp();
    }

    /**
     * Set up testing.
     */
    abstract void setUp();

    private boolean called = false;
    /**
     * Tests freezing of the level.
     */
    @Test
    public void testFreeze() {
        getLevel().freeze(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                called = true;
            }
        });
        assertFalse(called);
    }

    /**
     * Tests updating of level.
     */
    @Test
    public void testUpdate() {
        int beforeTime = (int) getLevel().getCurrentTime();
        getLevel().update();
        int afterTime = beforeTime + getLevel().getTimeMutation();
        assertEquals(afterTime, (int) getLevel().getCurrentTime());
    }

    /**
     * Tests time mutation of level.
     */
    @Test
    public void testGetTimeMutation() {
        assertEquals(getLevel().getTimeMutation(), -1);
    }

    /**
     * Get testable level.
     * @return level
     */
    abstract Level getLevel();
}
