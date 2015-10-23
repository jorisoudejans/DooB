package doob.level;

import doob.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Class to test LevelView. Can not test Draw because of Graphics.
 */
public class LevelViewTest {

    LevelView levelView;

    private Level level;

    private List<Player> players;
    private Player player1;
    private Player player2;

    private List<Ball> balls;
    private Ball ball1;
    private Ball ball2;

    private List<Wall> walls;
    private Wall wall1;
    private Wall wall2;

    private List<Projectile> projectiles;
    private Projectile projectile1;
    private Projectile projectile2;

    /**
     * Set up testing.
     */
    @Before
    public void setUp() {
        level = mock(Level.class);
        levelView = new LevelView(null, level);

        setUpPlayers();
        setUpBalls();
        setUpWalls();
    }

    /**
     * Set up players arrayList.
     */
    private void setUpPlayers() {
        players = new ArrayList<Player>();
        player1 = mock(Player.class);
        setUpProjectiles();
        when(player1.getProjectiles()).thenReturn(projectiles);
        player2 = mock(Player.class);
        players.add(player1);
        players.add(player2);
    }

    /**
     * Set up players arrayList.
     */
    private void setUpProjectiles() {
        projectiles = new ArrayList<Projectile>();
        projectile1 = mock(Projectile.class);
        projectile2 = mock(Projectile.class);
        projectiles.add(projectile1);
        projectiles.add(projectile2);
    }

    /**
     * Set up balls arrayList.
     */
    private void setUpBalls() {
        balls = new ArrayList<Ball>();
        ball1 = mock(Ball.class);
        ball2 = mock(Ball.class);
        balls.add(ball1);
        balls.add(ball2);
    }

    /**
     * Set up walls arrayList.
     */
    private void setUpWalls() {
        walls = new ArrayList<Wall>();
        wall1 = mock(Wall.class);
        wall2 = mock(Wall.class);
        walls.add(wall1);
        walls.add(wall2);
    }

    /**
     * Test movement of balls.
     */
    @Test
    public void testMove() {
        levelView.move(players, balls, walls);
        verify(ball1).move();
        verify(ball2).move();
    }

    /**
     * Tests if players move when they are alive.
     */
    @Test
    public void testPlayerMoveAlive() {
        when(player1.isAlive()).thenReturn(true);
        when(player2.isAlive()).thenReturn(true);
        levelView.move(players, balls, walls);
        verify(player1).move();
        verify(player2).move();
    }

    /**
     * Tests if players do not move when they are dead.
     */
    @Test
    public void testPlayerMoveDead() {
        when(player1.isAlive()).thenReturn(false);
        levelView.move(players, balls, walls);
        verify(player1, never()).move();
    }

    /**
     * Tests if walls move when they are moveable.
     */
    @Test
    public void testWallMoveMoveable() {
        when(wall1.isMoveable()).thenReturn(true);
        when(wall2.isMoveable()).thenReturn(true);
        levelView.move(players, balls, walls);
        verify(wall1).move();
        verify(wall2).move();
    }

    /**
     * Tests if walls do not move when they are not moveable.
     */
    @Test
    public void testWallMoveNotMoveable() {
        when(wall1.isMoveable()).thenReturn(false);
        levelView.move(players, balls, walls);
        verify(wall1, never()).move();
    }

    /**
     * Tests if projectiles move when they are not frozen.
     */
    @Test
    public void testProjectileNotFrozen() {
        when(level.isProjectileFreeze()).thenReturn(false);
        when(projectile1.getState()).thenReturn(Projectile.State.NORMAL);
        when(projectile2.getState()).thenReturn(Projectile.State.NORMAL);
        when(player1.isAlive()).thenReturn(true);
        when(player2.isAlive()).thenReturn(true);
        levelView.move(players, balls, walls);
        verify(projectile1).move();
        verify(projectile2).move();
    }

    /**
     * Tests if projectiles do not move when they are frozen.
     */
    @Test
    public void testProjectileFrozen() {
        levelView.move(players, balls, walls);
        when(level.isProjectileFreeze()).thenReturn(true);
        when(projectile1.getState()).thenReturn(Projectile.State.FROZEN);
        when(projectile2.getState()).thenReturn(Projectile.State.FROZEN);
        verify(projectile1, never()).move();
        verify(projectile2, never()).move();
    }
}