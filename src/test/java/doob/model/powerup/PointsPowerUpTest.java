package doob.model.powerup;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for TimePowerUp.
 */
public class PointsPowerUpTest extends PowerUpTest {

    private PointsPowerUp pointsPowerUp;

    /**
     * Initialize PointsPowerUp.
     */
    @Override
    public void initInstance() {
        pointsPowerUp = new PointsPowerUp();
    }

    /**
     * Tests getEndTime method for PointsPowerUp, should be 0.
     */
    @Test
    public void testGetEndTime() {
        assertEquals(0, pointsPowerUp.getActiveTime(), 0);
    }

    /**
     * Tests getTime method for PointsPowerUp, should be 0.
     */
    @Test
    public void testGetTime() {
        assertEquals(0, pointsPowerUp.getTime());
    }

    /**
     * Tests onActivate method. Should add time to level.
     */
    @Test
    public void testOnActivate() {
        when(getPlayer().getScore()).thenReturn(PointsPowerUp.POINTS_TO_ADD);

        pointsPowerUp.onActivate(getLevel(), getPlayer());

        verify(getPlayer())
                .setScore(PointsPowerUp.POINTS_TO_ADD + PointsPowerUp.POINTS_TO_ADD);
    }

    /**
     * Tests onDeactivate method. should do nothing...
     */
    @Test
    public void testOnDeactivate() {
        pointsPowerUp.onDeactivate(getLevel());

        verifyNoMoreInteractions(getLevel());
        verifyNoMoreInteractions(getPlayer());
    }

    /**
     * Get PowerUp instance.
     * @return instance of PowerUp.
     */
    @Override
    public PowerUp getPowerUp() {
        return pointsPowerUp;
    }

}
