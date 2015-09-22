package doob.model.powerup;

import doob.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Test class for PlayerSpeedPowerUpTest.
 */
public class PlayerSpeedPowerUpTest extends PowerUpTest {

    private PlayerSpeedPowerUp playerSpeedPowerUp;

    /**
     * Initialize ProtectOncePowerUp.
     */
    @Override
    public void initInstance() {
        playerSpeedPowerUp = new PlayerSpeedPowerUp();
    }

    /**
     * Tests getEndTime method for ProtectOncePowerUp.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(PlayerSpeedPowerUp.DURATION, playerSpeedPowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getDuration method for ProtectOncePowerUp.
     */
    @Test
    public void testGetTime() {
        assertEquals(PlayerSpeedPowerUp.DURATION, playerSpeedPowerUp.getDuration());
    }

    /**
     * Tests onActivate method. Should add time to level.
     */
    @Test
    public void testOnActivate() {
        playerSpeedPowerUp.onActivate(getLevel(), getPlayer());

        verify(getPlayer())
                .setMoveSpeed(Player.START_SPEED * PlayerSpeedPowerUp.SPEED_FACTOR);
    }

    /**
     * Tests onDeactivate method. should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        playerSpeedPowerUp.setPlayer(getPlayer());
        playerSpeedPowerUp.onDeactivate(getLevel());

        verify(getPlayer())
                .setMoveSpeed(Player.START_SPEED);
    }

    /**
     * Get PowerUp instance.
     * @return instance of PowerUp.
     */
    @Override
    public PowerUp getPowerUp() {
        return playerSpeedPowerUp;
    }

}
