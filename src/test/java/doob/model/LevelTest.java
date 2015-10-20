package doob.model;

import doob.util.BoundsTuple;
import javafx.scene.canvas.Canvas;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

/**
 * Tests for Level.
 * Created by hidde on 9/10/15.
 */
public class LevelTest {

    private Wall leftWall;
    private Wall rightWall;
    private Wall floor;
    private ArrayList<Wall> walls;

    private BoundsTuple bounds;

    @Before
    public void setUp() {
        leftWall = new Wall(0, 0, 50, 500);
        rightWall = new Wall(1000, 0, 50, 500);
        walls = new ArrayList<Wall>();
        walls.add(leftWall);
        walls.add(rightWall);
        floor = new Wall(0, 500, 1000, 20);
        bounds = new BoundsTuple(1000.0, 1000.0);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBasic() {
        new Level(bounds);
    }

    /**
     * Generates a basic level with basic walls.
     * @return Standard level
     */
    private Level basicLevel() {
        Level level = new Level(bounds);
        level.setLeft(leftWall);
        level.setRight(rightWall);
        level.setFloor(floor);
        level.setWalls(walls);
        return level;
    }

    /**
     * Generates a level with one player and one ball.
     * @return level with player and ball
     */
    private Level complicatedLevel() {
        Level level = basicLevel();
        Ball ball = new Ball(100, 0, 1, 1, 20);
        ArrayList<Ball> balls = new ArrayList<Ball>();
        balls.add(ball);
        level.setBalls(balls);
        Player player = new Player(100, 0, 20, 20, null, null, null);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        level.setPlayers(players);
        return level;
    }

    @Test
    public void testSpawnSameSizeBalls1() {
        SurvivalLevel level = survivalBallsSetup();

        level.spawnSameSizeBalls(3, 32);

        assertEquals((double) 250, level.getBalls().get(0).getX(), 1);
        assertEquals((double) 750, level.getBalls().get(2).getX(), 1);
    }

    @Test
    public void testSpawnSameSizeBalls2() {
        SurvivalLevel level = survivalBallsSetup();

        level.spawnSameSizeBalls(2, 64);

        assertEquals(2, level.getBalls().size());
        assertEquals((double) 125, level.getBalls().get(0).getY(), 1);
    }

    @Test
    public void testSpawnBalls1(){
        SurvivalLevel level = survivalBallsSetup();

        level.spawnBalls(98);
        assertEquals(3, level.getBalls().size());
        assertEquals(32, level.getBalls().get(0).getSize());
    }

    @Test
    public void testSpawnBalls2() {
        SurvivalLevel level = survivalBallsSetup();

        level.spawnBalls(100);
        assertEquals(2, level.getBalls().size());
        assertEquals(64, level.getBalls().get(0).getSize());
    }

    public SurvivalLevel survivalBallsSetup(){
        SurvivalLevel level = new SurvivalLevel(new BoundsTuple(1000.0, 500.0));

        ArrayList<Ball> balls = new ArrayList<Ball>();
        level.setBalls(balls);

        return level;
    }
}
