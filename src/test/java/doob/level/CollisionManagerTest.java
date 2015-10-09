package doob.level;

import doob.model.*;

import doob.model.powerup.LifePowerUp;
import doob.model.powerup.PowerUp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Tests CollisionManager
 * Created by hidde on 10/6/15.
 */
public class CollisionManagerTest {

    @Mock
    private Level level;
    @Mock
    private CollisionResolver collisionResolver;

    private CollisionManager collisionManager;

    /**
     * Init level mock.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        collisionManager = new CollisionManager(level, collisionResolver);
    }

    /**
     * Detect that no collisions are detected when they shouldnt.
     */
    @Test
    public void testNoCollisions() {
        collisionManager.detectCollisions();
        verifyNoMoreInteractions(collisionResolver);
    }

    /**
     * Tests collision between player and wall.
     */
    @Test
    public void testPlayerVersusWall() {
        Player player = new Player(0, 0, 1000, 1000, null, null, null);
        Wall wall = new Wall(0, 0, 100, 100);

        when(level.getPlayers()).thenReturn(asList(player));
        when(level.getWalls()).thenReturn(asList(wall));

        collisionManager.detectCollisions();
        verify(collisionResolver).playerVersusWall(player, wall);
    }

    /**
     * Tests non-existing collision between player and wall.
     */
    @Test
    public void testPlayerVersusWallNoCollision() {
        Player player = new Player(200, 0, 1000, 1000, null, null, null);
        Wall wall = new Wall(0, 0, 100, 100);

        when(level.getPlayers()).thenReturn(asList(player));
        when(level.getWalls()).thenReturn(asList(wall));

        collisionManager.detectCollisions();
        verifyNoMoreInteractions(collisionResolver);
    }

    /**
     * Tests collision between player and powerup.
     */
    @Test
    public void testPlayerVersusPowerUp() {
        Player player = new Player(0, 0, 1000, 1000, null, null, null);
        PowerUp powerup = new LifePowerUp();

        when(level.getPlayers()).thenReturn(asList(player));
        when(level.getCollidablePowerups()).thenReturn(asList(powerup));

        collisionManager.detectCollisions();
        verify(collisionResolver).playerVersusPowerUp(player, powerup);
    }

    /**
     * Tests non-existing collision between player and powerup.
     */
    @Test
    public void testPlayerVersusPowerUpNoCollision() {
        Player player = new Player(0, 0, 1000, 1000, null, null, null);
        PowerUp powerup = new LifePowerUp();
        powerup.setLocationX(2000); // move powerup out of collision

        when(level.getPlayers()).thenReturn(asList(player));
        when(level.getCollidablePowerups()).thenReturn(asList(powerup));

        collisionManager.detectCollisions();
        verifyNoMoreInteractions(collisionResolver);
    }

    /**
     * Tests collision between ball and wall.
     */
    @Test
    public void testBallVersusWall() {
        Ball ball = new Ball(0, 0, 0, 0, 50);
        Wall wall = new Wall(0, 0, 100, 100);

        when(level.getBalls()).thenReturn(asList(ball));
        when(level.getWalls()).thenReturn(asList(wall));

        collisionManager.detectCollisions();
        verify(collisionResolver).ballVersusWall(ball, wall);
    }

    /**
     * Tests non-existing collision between ball and wall.
     */
    @Test
    public void testBallVersusWallNoCollision() {
        Ball ball = new Ball(0, 0, 0, 0, 50);
        Wall wall = new Wall(100, 0, 100, 100);

        when(level.getBalls()).thenReturn(asList(ball));
        when(level.getWalls()).thenReturn(asList(wall));

        collisionManager.detectCollisions();
        verifyNoMoreInteractions(collisionResolver);
    }

    /**
     * Tests collision between ball and projectile.
     */
    @Test
    public void testBallVersusProjectile() {
        Ball ball = new Ball(0, 0, 0, 0, 50);
        Projectile projectile = new Spike(10, 10, -1);

        when(level.getBalls()).thenReturn(asList(ball));
        when(level.getProjectiles()).thenReturn(asList(projectile));

        collisionManager.detectCollisions();
        verify(collisionResolver).ballVersusProjectile(ball, projectile);
    }

    /**
     * Tests non-existing collision between ball and projectile.
     */
    @Test
    public void testBallVersusProjectileNoCollision() {
        Ball ball = new Ball(0, 0, 0, 0, 50);
        Projectile projectile = new Spike(100, 10, -1);

        when(level.getBalls()).thenReturn(asList(ball));
        when(level.getProjectiles()).thenReturn(asList(projectile));

        collisionManager.detectCollisions();
        verifyNoMoreInteractions(collisionResolver);
    }

    /**
     * Tests projectile that hits the ceiling.
     */
    @Test
    public void testProjectileVersusCeiling() {
        Projectile projectile = new Spike(0, -10, -1);

        when(level.getProjectiles()).thenReturn(asList(projectile));

        collisionManager.detectCollisions();
        verify(collisionResolver).projectileVersusCeiling(projectile);
    }

    /**
     * Tests projectile that hits the ceiling.
     */
    @Test
    public void testProjectileVersusCeilingNoHit() {
        Projectile projectile = new Spike(0, 10, -1);

        when(level.getProjectiles()).thenReturn(asList(projectile));

        collisionManager.detectCollisions();
        verifyNoMoreInteractions(collisionResolver);
    }

    /**
     * Converts single object into an one-item arraylist.
     * @param object the single object
     * @param <T> lists' type
     * @return array list with one item of type T
     */
    private <T> ArrayList<T> asList(T object) {
        ArrayList<T> list = new ArrayList<T>();
        list.add(object);
        return list;
    }
}
