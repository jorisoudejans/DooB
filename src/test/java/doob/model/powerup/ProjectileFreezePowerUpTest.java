package doob.model.powerup;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Test class for ProjectileFreezePowerUpTest.
 */
public class ProjectileFreezePowerUpTest extends PowerUpTest {

    private ProjectileFreezePowerUp projectileFreezePowerUp;

    /**
     * Initialize ProjectileFreezePowerUp.
     */
    @Override
    public void initInstance() {
        projectileFreezePowerUp = new ProjectileFreezePowerUp();
    }

    /**
     * Tests getEndTime method for ProjectileFreezePowerUp.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(ProjectileFreezePowerUp.DURATION, projectileFreezePowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getDuration method for ProjectileFreezePowerUp.
     */
    @Test
    public void testGetTime() {
        assertEquals(ProjectileFreezePowerUp.DURATION, projectileFreezePowerUp.getDuration());
    }

    /**
     * Tests onActivate method. Should add time to level.
     */
    @Test
    public void testOnActivate() {
        projectileFreezePowerUp.onActivate(getLevel(), getPlayer());

        verify(getLevel()).setProjectileFreeze(true);
    }

    /**
     * Tests onDeactivate method. should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        projectileFreezePowerUp.onDeactivate(getLevel());

        verify(getLevel()).setProjectileFreeze(false);
    }

    /**
     * Get PowerUp instance.
     * @return instance of PowerUp.
     */
    @Override
    public PowerUp getPowerUp() {
        return projectileFreezePowerUp;
    }

}
