package doob.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Before
    public void setUp() {
        leftWall = new Wall(0, 0, 50, 500);
        rightWall = new Wall(1000, 0, 50, 500);
        floor = new Wall(0, 500, 1000, 20);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBasic() {
        new Level(canvas);
        verify(canvas).requestFocus();
    }

    /*@Test
    public void testBallWallCollisionLeftWall() {
        Level level = basicLevel();
        Ball ball = new Ball(0, 0, 1, 1, 1);
        ball.setBallSpeed(100);
        ArrayList<Ball> balls = new ArrayList<Ball>();
        balls.add(ball);
        level.setBalls(balls);

        level.ballWallCollision();
        assertEquals(100, ball.getSpeedX(), 0.01); // the collision method changed the ball speed
    }*/


    /*
    @Test
    public void testPlayerWallCollisionLeftWall() {
        Level level = basicLevel();
        Player player = new Player(-10, 0, 20, 20, null, null, null);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        level.setPlayers(players);

        level.playerWallCollision();
        assertEquals(0, player.getX()); // moving into left wall set player x to 0
    }
    */

    //Test not valid anymore
    /*
    @Test
    public void testPlayerWallCollisionNoChange() {
        Level level = basicLevel();
        Player player = new Player(100, 0, 20, 20, null, null, null);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        level.setPlayers(players);

        level.playerWallCollision();
        assertEquals(100, player.getX()); // moving into nothing didn't change anything
    }
    */


    @Test
    public void testPlayerBallCollision() {
        Level level = basicLevel();
        Ball ball = new Ball(100, 0, 1, 1, 1);
        ArrayList<Ball> balls = new ArrayList<Ball>();
        balls.add(ball);
        level.setBalls(balls);
        Player player = new Player(100, 0, 20, 20, null, null, null);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        level.setPlayers(players);

        assertTrue(level.ballPlayerCollision()); // moving into nothing didn't change anything
    }

    @Test
    public void testPlayerBallCollisionFalse() {
        Level level = basicLevel();
        Ball ball = new Ball(200, 0, 1, 1, 1);
        ArrayList<Ball> balls = new ArrayList<Ball>();
        balls.add(ball);
        level.setBalls(balls);
        Player player = new Player(100, 0, 20, 20, null, null, null);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        level.setPlayers(players);

        assertFalse(level.ballPlayerCollision()); // moving into nothing didn't change anything
    }

    /* == TODO: Fix by Jasper
    @Test
    public void testBallProjectileCollision() {
        Level level = basicLevel();
        level.setShootSpeed(-1); // indicate testing
        Ball ball = new Ball(100, 0, 1, 1, 20);
        ArrayList<Ball> balls = new ArrayList<Ball>();
        balls.add(ball);
        level.setBalls(balls);
        Player player = new Player(100, 0, 20, 20, null, null, null);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        level.setPlayers(players);

        level.shoot(player); // creates projectile
        level.ballProjectileCollision(); // removes ball
        assertEquals(100, level.getScore());
    }

    @Test
    public void testProjectileCeilingCollision() {
        Level level = basicLevel();
        level.setShootSpeed(-1);
        Player player = new Player(100, -10, 20, 20, null, null, null);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        level.setPlayers(players);

        level.shoot(player); // creates projectile
        level.projectileCeilingCollision(); // removes ball
        assertNotNull(level);
    }*/

    /*
    @Test
    public void testLevelTimeUpdateOnce() {
        Level level = complicatedLevel();

        level.setCurrentTime(5.0);
        level.update();
        assertEquals(4.0, level.getCurrentTime(), 0.01);
    }

    @Test
    public void testLevelTimeUpdateTwice() {
        Level level = complicatedLevel();

        level.setCurrentTime(5.0);
        level.update();
        level.update();
        assertEquals(3.0, level.getCurrentTime(), 0.01);
    }
    */

    /*@Test
    public void testLevelTimeGameOver() {
        Level level = complicatedLevel();

        level.setCurrentTime(5.0);
        level.setTime(10.0);
        //level.gameOver();
        //assertEquals(10.0, level.getCurrentTime(), 0.01);
    }*/

    /**
     * Generates a basic level with basic walls.
     * @return Standard level
     */
    private Level basicLevel() {
        Level level = new Level(canvas);
        level.setLeft(leftWall);
        level.setRight(rightWall);
        level.setFloor(floor);
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
