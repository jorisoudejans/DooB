package doob.model.powerup;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Test class for LifePowerUp.
 */
public class LifePowerUpTest extends PowerUpTest {

    private LifePowerUp lifePowerUp;

    /**
     * Init LifePowerUp.
     */
    @Override
    public void initInstance() {
        lifePowerUp = new LifePowerUp();
    }

    /**
     * Tests getEndTime method for LifePowerUp, should be 0.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(0, lifePowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getDuration method for LifePowerUp, should be 0.
     */
    @Test
    public void testGetTime() {
        assertEquals(0, lifePowerUp.getDuration());
    }

    /**
     * Tests onActivate method. Should add 1 life.
     */
    @Test
    public void testOnActivate() {
        when(getPlayer().getLives()).thenReturn(0);

        lifePowerUp.onActivate(getLevel(), getPlayer());

        verify(getPlayer()).setLives(1);
    }

    /**
     * Tests onDeactivate method. Should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        lifePowerUp.onDeactivate(getLevel());

        verifyNoMoreInteractions(getLevel());
        verifyNoMoreInteractions(getPlayer());
    }

    /**
     * Get used instance.
     * @return PowerUp instance.
     */
    public PowerUp getPowerUp() {
        return lifePowerUp;
    }

}
