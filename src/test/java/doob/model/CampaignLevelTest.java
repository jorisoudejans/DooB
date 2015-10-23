package doob.model;

import doob.util.BoundsTuple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for Survival Level.
 */
public class CampaignLevelTest extends LevelTest {

    private CampaignLevel level;

    /**
     * Set up all objects.
     */
    public void setUp() {
        level = new CampaignLevel(new BoundsTuple(CANVAS_DIMENSION, CANVAS_DIMENSION));
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
     * Test time mutation of survival level.
     */
    @Test
    public void testGetTimeMutation() {
        assertEquals(getLevel().getTimeMutation(), -1);
    }

    /**
     * Get tested level.
     * @return level
     */
    public Level getLevel() {
        return level;
    }

}
