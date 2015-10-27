package doob.model.level;

import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;
import doob.util.BoundsTuple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for Survival Level.
 */
// Lots of numbers needed for integer testing.
@SuppressWarnings("magicnumber")
public class SurvivalLevelTest extends LevelTest {

    private SurvivalLevel level;

    /**
     * Set up testable level.
     */
    public void setUp() {
        level = new SurvivalLevel(new BoundsTuple(CANVAS_DIMENSION, CANVAS_DIMENSION));
        Wall wall1 = mock(Wall.class);
        Wall wall2 = mock(Wall.class);
        List<Wall> walls = new ArrayList<Wall>();
        walls.add(wall1);
        walls.add(wall2);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        List<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);

        List<Ball> balls = new ArrayList<Ball>();

        level.setPlayers(players);
        level.setWalls(walls);
        level.setBalls(balls);
    }

    /**
     * Tests ball spawn of two same sized balls.
     */
    @Test
    public void testSpawnSameSizeBalls1() {
        level.spawnSameSizeBalls(3, 32);

        assertEquals((double) 250, level.getBalls().get(0).getX(), 1);
        assertEquals((double) 750, level.getBalls().get(2).getX(), 1);
    }

    /**
     * Tests spawn two balls of the same size.
     */
    @Test
    public void testSpawnSameSizeBalls2() {
        level.spawnSameSizeBalls(2, 64);

        assertEquals(2, level.getBalls().size());
        assertEquals((double) 250, level.getBalls().get(0).getY(), 1);
    }

    /**
     * Tests one ball spawn.
     */
    @Test
    public void testSpawnBalls1() {
        level.spawnBalls(98);
        assertEquals(3, level.getBalls().size());
        assertEquals(32, level.getBalls().get(0).getSize());
    }

    /**
     * Tests two ball spawns.
     */
    @Test
    public void testSpawnBalls2() {
        level.spawnBalls(100);
        assertEquals(2, level.getBalls().size());
        assertEquals(64, level.getBalls().get(0).getSize());
    }

    /**
     * Test time mutation of survival level.
     */
    @Test
    public void testGetTimeMutation() {
        assertEquals(level.getTimeMutation(), 1);
    }

    /**
     * Get tested level.
     * @return level
     */
    public Level getLevel() {
        return level;
    }

}
