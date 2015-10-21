package doob.model;

import doob.util.BoundsTuple;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

/**
 * Tests for Level.
 * Created by hidde on 9/10/15.
 */
public class LevelTest {

    @SuppressWarnings("CheckStyle")
    protected Level level;

    private static final double CANVAS_DIMENSION = 1000;

    /**
     * Set up all objects.
     */
    @Before
    public void setUp() {
        level = getLevel(new BoundsTuple(CANVAS_DIMENSION, CANVAS_DIMENSION));
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

    protected Level getLevel(BoundsTuple bounds) {
        return new Level(bounds);
    }

    private boolean called = false;

    /**
     * Tests freezing of the level.
     */
    @Test
    public void testFreeze() {
        level.freeze(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                called = true;
            }
        });
        assertFalse(called);
    }

    /**
     * Tests updating of level.
     */
    @Test
    public void testUpdate() {
        int beforeTime = (int) level.getCurrentTime();
        level.update();
        int afterTime = beforeTime + level.getTimeMutation();
        assertEquals(afterTime, (int) level.getCurrentTime());
    }

    /**
     * Tests time mutation of level.
     */
    @Test
    public void testGetTimeMutation() {
        assertEquals(level.getTimeMutation(), -1);
    }
}
