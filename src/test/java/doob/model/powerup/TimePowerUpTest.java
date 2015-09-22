package doob.model.powerup;

import doob.model.Level;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Should extend TimePowerUp.
 */
public class TimePowerUpTest {

    @Test
    public void testGetEndTime() {
        TimePowerUp timePowerUp = new TimePowerUp();
        assertEquals(1, timePowerUp.getActiveTime(), 0.01);
    }

    @Test
    public void testGetTime() {
        TimePowerUp timePowerUp = new TimePowerUp();
        assertEquals(1, timePowerUp.getTime());
    }

    @Test
    public void testOnActivate() {
        Level level = mock(Level.class);
        when(level.getCurrentTime()).thenReturn((double) TimePowerUp.CYCLES_TO_ADD);

        TimePowerUp timePowerUp = new TimePowerUp();
        timePowerUp.onActivate(level);

        verify(level).setCurrentTime(TimePowerUp.CYCLES_TO_ADD + PowerUp.DEFAULT_WAIT_CYCLES);
    }

    @Test
    public void testOnDeactivate() {
        Level level = mock(Level.class);

        TimePowerUp timePowerUp = new TimePowerUp();
        timePowerUp.onDeactivate(level);
    }

    @Test
    public void testSetEndTime() {
        TimePowerUp timePowerUp = new TimePowerUp();
        timePowerUp.setActiveTime(10);
    }
}
