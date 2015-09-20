package doob.model.powerup;

import doob.model.Level;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Should extend TimePowerUp.
 *
 * Created by hidde on 9/10/15.
 */
public class TimePowerUpTest {

    @Test
    public void testGetEndtime() {

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
        when(level.getCurrentTime()).thenReturn(500.0);

        TimePowerUp timePowerUp = new TimePowerUp();
        timePowerUp.onActivate(level);

        verify(level).setCurrentTime(1000.0);

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
