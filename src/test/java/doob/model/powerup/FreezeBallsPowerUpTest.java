package doob.model.powerup;

import doob.model.Level;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for FreezeBallsPowerUpTest.
 */
public class FreezeBallsPowerUpTest extends PowerUpTest {

    private FreezeBallsPowerUp freezeBallsPowerUp;

    /**
     * Initialize ProtectOncePowerUp.
     */
    @Override
    public void initInstance() {
        freezeBallsPowerUp = new FreezeBallsPowerUp();
    }

    /**
     * Tests getEndTime method for ProtectOncePowerUp.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(FreezeBallsPowerUp.DURATION, freezeBallsPowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getDuration method for ProtectOncePowerUp.
     */
    @Test
    public void testGetTime() {
        assertEquals(FreezeBallsPowerUp.DURATION, freezeBallsPowerUp.getDuration());
    }

    /**
     * Tests onActivate method. Should add time to level.
     */
    @Test
    public void testOnActivate() {
        when(getLevel().getState()).thenReturn(Level.State.NORMAL);

        freezeBallsPowerUp.onActivate(getLevel(), getPlayer());

        verify(getLevel())
                .setState(Level.State.BALLS_FREEZE);
    }

    /**
     * Tests onDeactivate method. should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        when(getLevel().getState()).thenReturn(Level.State.BALLS_FREEZE);

        freezeBallsPowerUp.setPlayer(getPlayer());
        freezeBallsPowerUp.onDeactivate(getLevel());

        verify(getLevel())
                .setState(Level.State.NORMAL);
    }

    /**
     * Get PowerUp instance.
     * @return instance of PowerUp.
     */
    @Override
    public PowerUp getPowerUp() {
        return freezeBallsPowerUp;
    }

}
