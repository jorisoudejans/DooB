package doob.model;

import doob.util.BoundsTuple;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for Level.
 * Created by hidde on 9/10/15.
 */
public class SurvivalLevelTest extends LevelTest {

    @Override
    protected SurvivalLevel getLevel(BoundsTuple bounds) {
        return new SurvivalLevel(bounds);
    }

    /**
     * Test constructor for errors.
     */
    @Test
    public void testBasic() {

    }

    /**
     * Tests ball spawn of two same sized balls.
     */
    @Test
    public void testSpawnSameSizeBalls1() {
        ((SurvivalLevel) level).spawnSameSizeBalls(3, 32);

        assertEquals((double) 250, level.getBalls().get(0).getX(), 1);
        assertEquals((double) 750, level.getBalls().get(2).getX(), 1);
    }

    /**
     * Tests spawn two balls of the same size.
     */
    @Test
    public void testSpawnSameSizeBalls2() {
        ((SurvivalLevel) level).spawnSameSizeBalls(2, 64);

        assertEquals(2, level.getBalls().size());
        assertEquals((double) 250, level.getBalls().get(0).getY(), 1);
    }

    /**
     * Tests one ball spawn.
     */
    @Test
    public void testSpawnBalls1() {
        ((SurvivalLevel) level).spawnBalls(98);
        assertEquals(3, level.getBalls().size());
        assertEquals(32, level.getBalls().get(0).getSize());
    }

    /**
     * Tests two ball spawns.
     */
    @Test
    public void testSpawnBalls2() {
        ((SurvivalLevel) level).spawnBalls(100);
        assertEquals(2, level.getBalls().size());
        assertEquals(64, level.getBalls().get(0).getSize());
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testGetTimeMutation() {
        assertEquals(level.getTimeMutation(), 1);
    }

}
