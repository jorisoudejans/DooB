package doob.model.powerup;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Test class for TimePowerUp.
 */
public class TimePowerUpTest extends PowerUpTest {

    private TimePowerUp timePowerUp;

    /**
     * Initialize TimePowerUp.
     */
    @Override
    public void initInstance() {
        timePowerUp = new TimePowerUp();
    }

    /**
     * Tests getEndTime method for TimePowerUp, should be 0.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(0, timePowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getDuration method for TimePowerUp, should be 0.
     */
    @Test
    public void testGetTime() {
        assertEquals(0, timePowerUp.getDuration());
    }

    /**
     * Tests onActivate method. Should add time to level.
     */
    @Test
    public void testOnActivate() {
        when(getLevel().getCurrentTime()).thenReturn((double) TimePowerUp.CYCLES_TO_ADD);
        when(getLevel().getTime()).thenReturn(TimePowerUp.CYCLES_TO_ADD + PowerUp.DEFAULT_WAIT_CYCLES);
        timePowerUp.onActivate(getLevel(), getPlayer());

        verify(getLevel()).setCurrentTime(TimePowerUp.CYCLES_TO_ADD + PowerUp.DEFAULT_WAIT_CYCLES);
    }

    /**
     * Tests onDeactivate method. should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        timePowerUp.onDeactivate(getLevel());

        verifyNoMoreInteractions(getLevel());
        verifyNoMoreInteractions(getPlayer());
    }

    /**
     * Get PowerUp instance.
     * @return instance of PowerUp.
     */
    @Override
    public PowerUp getPowerUp() {
        return timePowerUp;
    }

}
