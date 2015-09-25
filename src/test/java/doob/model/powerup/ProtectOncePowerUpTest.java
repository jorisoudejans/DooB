package doob.model.powerup;

import doob.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for ProtectOncePowerUp.
 */
public class ProtectOncePowerUpTest extends PowerUpTest {

    private ProtectOncePowerUp protectOncePowerUp;

    /**
     * Initialize ProtectOncePowerUp.
     */
    @Override
    public void initInstance() {
        protectOncePowerUp = new ProtectOncePowerUp();
    }

    /**
     * Tests getEndTime method for ProtectOncePowerUp.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(ProtectOncePowerUp.DURATION, protectOncePowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getDuration method for ProtectOncePowerUp.
     */
    @Test
    public void testGetTime() {
        assertEquals(ProtectOncePowerUp.DURATION, protectOncePowerUp.getDuration());
    }

    /**
     * Tests onActivate method. Should add time to level.
     */
    @Test
    public void testOnActivate() {
        when(getPlayer().getState()).thenReturn(Player.State.NORMAL);

        protectOncePowerUp.onActivate(getLevel(), getPlayer());

        verify(getPlayer())
                .setState(Player.State.INVULNERABLE);
    }

    /**
     * Tests onDeactivate method. should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        when(getPlayer().getState()).thenReturn(Player.State.INVULNERABLE);

        protectOncePowerUp.setPlayer(getPlayer());
        protectOncePowerUp.onDeactivate(getLevel());

        verify(getPlayer())
                .setState(Player.State.NORMAL);
    }

    /**
     * Get PowerUp instance.
     * @return instance of PowerUp.
     */
    @Override
    public PowerUp getPowerUp() {
        return protectOncePowerUp;
    }

}
