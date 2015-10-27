package doob.model.powerup;

import doob.model.level.Level;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Test class for ProjectileSpeedPowerUpTest.
 */
public class ProjectileSpeedPowerUpTest extends PowerUpTest {

    private ProjectileSpeedPowerUp projectileSpeedPowerUp;

    /**
     * Initialize ProjectileSpeedPowerUp.
     */
    @Override
    public void initInstance() {
        projectileSpeedPowerUp = new ProjectileSpeedPowerUp();
    }

    /**
     * Tests getEndTime method for ProjectileSpeedPowerUp.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(ProjectileSpeedPowerUp.DURATION, projectileSpeedPowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getDuration method for ProjectileSpeedPowerUp.
     */
    @Test
    public void testGetTime() {
        assertEquals(ProjectileSpeedPowerUp.DURATION, projectileSpeedPowerUp.getDuration());
    }

    /**
     * Tests onActivate method. Should add time to level.
     */
    @Test
    public void testOnActivate() {
        projectileSpeedPowerUp.onActivate(getLevel(), getPlayer());

        Assert.assertEquals(
                Level.getProjectileSpeed(),
                Level.PROJECTILE_START_SPEED * ProjectileSpeedPowerUp.SPEED_FACTOR
        );
    }

    /**
     * Tests onDeactivate method. should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        projectileSpeedPowerUp.onDeactivate(getLevel());

        Assert.assertEquals(
                Level.getProjectileSpeed(),
                Level.PROJECTILE_START_SPEED
        );
    }

    /**
     * Get PowerUp instance.
     * @return instance of PowerUp.
     */
    @Override
    public PowerUp getPowerUp() {
        return projectileSpeedPowerUp;
    }

}
