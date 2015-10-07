package doob.model;

import javafx.scene.canvas.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

/**
 * Created by hidde on 9/10/15.
 */
public class LevelTest {

    @Spy
    private Canvas canvas;

    private Wall leftWall;
    private Wall rightWall;
    private Wall floor;
    private ArrayList<Wall> walls;

    @Before
    public void setUp() {
        leftWall = new Wall(0, 0, 50, 500);
        rightWall = new Wall(1000, 0, 50, 500);
        walls = new ArrayList<Wall>();
        walls.add(leftWall);
        walls.add(rightWall);
        floor = new Wall(0, 500, 1000, 20);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBasic() {
        new Level(canvas);
        verify(canvas).requestFocus();
    }

    /**
     * Generates a basic level with basic walls.
     * @return Standard level
     */
    private Level basicLevel() {
        Level level = new Level(canvas);
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
}
