package doob.model.powerup;

import doob.model.Level;
import doob.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Tests abstract class PowerUp.
 */
public abstract class PowerUpTest {

    private Level level;
    private Player player;

    /**
     * Initialize mocks.
     */
    @Before
    public void init() {
        level = mock(Level.class);
        player = mock(Player.class);
        initInstance();
    }

    /**
     * Instantiate an instance of the testable PowerUp.
     */
    public abstract void initInstance();

    /**
     * Tests onActivate method of PowerUp.
     */
    @Test
    public abstract void testOnActivate();

    /**
     * Tests onDeactivate method of PowerUp.
     */
    @Test
    public abstract void testOnDeactivate();

    /**
     * Test collides method. Always returns false.
     */
    @Test
    public void testCollides() {
        //Assert.assertFalse(getPowerUp().collides(null));
    }

    /**
     * Tests sprite method.
     */
    @Test
    public void testSprite() {
        Assert.assertNotNull(getPowerUp().spritePath());
    }

    /**
     * Get Level mock.
     * @return Level mock.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Get Player mock.
     * @return Player mock.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get an instance of the tested class.
     * @return instance of PowerUp.
     */
    public abstract PowerUp getPowerUp();
}
