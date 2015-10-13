package doob.level;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javafx.scene.media.MediaException;

import org.junit.Before;
import org.junit.Test;

import doob.model.Ball;
import doob.model.Level;
import doob.model.Player;
import doob.model.Projectile;
import doob.model.Wall;
import doob.model.powerup.PowerUp;

/**
 * Class to test all resolving of collisions.
 */
public class CollisionResolverTest {

    private CollisionResolver collisionResolver;
    private Level level;
    private static final int MEDIUM_SIZE_BALL = 40;
    private static final int PLAYER_LIVES = 3;

    /**
     * Create instance of CollisionResolver.
     */
    @Before
    public void setUp() {
        level = mock(Level.class);
        collisionResolver = new CollisionResolver(level);
    }

    /**
     * Test player versus wall when wall is open.
     */
    @Test
    public void testPlayerVersusWallOpen() {
        Player player = mock(Player.class);
        Wall wall = mock(Wall.class);
        when(wall.isOpen()).thenReturn(true);
        when(player.getSpeed()).thenReturn(0);

        collisionResolver.playerVersusWall(player, wall);
        verify(player, never()).setX(anyInt());
    }

    /**
     * Test player versus wall when speed is positive.
     */
    @Test
    public void testPlayerVersusWallPositiveSpeed() {
        Player player = mock(Player.class);
        Wall wall = mock(Wall.class);
        when(player.getSpeed()).thenReturn(1);

        collisionResolver.playerVersusWall(player, wall);
        verify(player).setX(anyInt());
    }
    
    /**
     * Test if an invulnerable player cannot die.
     */
    @Test
    public void invulnerablePlayerTest() {
    	Player player = mock(Player.class);
    	Ball ball = mock(Ball.class);
    	when(player.getState()).thenReturn(Player.State.INVULNERABLE);
    	collisionResolver.playerVersusBall(player, ball);
    	verify(player, never()).die();
    }
    
    /**
     * Test if a player which is not invulnerable dies when colliding with a ball.
     */
    @Test
    public void playerVersusBallGameOverTest() {
    	Player player = mock(Player.class);
    	Ball ball = mock(Ball.class);
    	when(player.getState()).thenReturn(Player.State.NORMAL);
    	collisionResolver.playerVersusBall(player, ball);
    	verify(player).die();
    	when(player.getLives()).thenReturn(1);
    	verify(this.level).onEvent(Level.Event.ZERO_LIVES);    	
    }
    
    /**
     * Test if a player which is not invulnerable dies when colliding with a ball.
     */
    @Test
    public void playerVersusBallTest() {
    	Player player = mock(Player.class);
    	Ball ball = mock(Ball.class);
    	when(player.getState()).thenReturn(Player.State.NORMAL);
    	when(player.getLives()).thenReturn(PLAYER_LIVES);
    	collisionResolver.playerVersusBall(player, ball);
    	verify(player).die();
    	verify(this.level).onEvent(Level.Event.LOST_LIFE);
    	//verify(this.level).freeze(Level.Event.LOST_LIFE);
    }
    
    /**
     * Tests a collision between a player and a powerup.
     */
    @Test
    public void playerVersusPowerUpTest() {
    	Player player = mock(Player.class);
    	PowerUp powerup = mock(PowerUp.class);
    	PowerUpManager p = mock(PowerUpManager.class);
    	when(level.getPowerUpManager()).thenReturn(p);
    	collisionResolver.playerVersusPowerUp(player, powerup);
    	verify(this.level).getPowerUpManager();    	
    }
    
    /**
     * Test bouncing on the floor.
     */
    @Test
    public void ballVersusFloorTest() {
    	Wall wall = mock(Wall.class);
    	Ball ball = mock(Ball.class);
    	when(level.getFloor()).thenReturn(wall);
    	collisionResolver.ballVersusWall(ball, wall);
    	verify(ball).setSpeedY(anyInt());
    }
    
    /**
     * Test collision of a ball with a moving wall.
     */
    @Test
    public void ballVersusMovingWallTest() {
    	Wall wall = mock(Wall.class);
    	Ball ball = mock(Ball.class);
    	when(wall.isMoveable()).thenReturn(true);
    	collisionResolver.ballVersusWall(ball, wall);
    	verify(level).removeBall(ball);    	
    }
    
    /**
     * Test collision of a ball with the ceiling.
     */
    @Test
    public void ballVersusCeilingTest() {
    	Wall wall = mock(Wall.class);
    	Ball ball = mock(Ball.class);
    	when(level.getFloor()).thenReturn(new Wall(1, 1, 1, 1));
    	when(level.getCeiling()).thenReturn(wall);
    	when(wall.isMoveable()).thenReturn(false);
    	collisionResolver.ballVersusWall(ball, wall);
    	verify(level).removeBall(ball);    	
    }
    
    /**
     * Test ball collision with normal wall.
     */
    @Test
    public void ballVersusWallTest() {
    	Wall wall = mock(Wall.class);
    	Ball ball = mock(Ball.class);
    	when(level.getFloor()).thenReturn(new Wall(1, 1, 1, 1));
    	when(level.getCeiling()).thenReturn(new Wall(1, 1, 1, 1));
    	when(wall.isMoveable()).thenReturn(false);
    	when(ball.getSpeedX()).thenReturn(2.0);
    	collisionResolver.ballVersusWall(ball, wall);
    	verify(ball).setSpeedX(-1 * 2.0);    	
    }
    
    /**
     * Test ball versus projectile test.
     */
    @Test
    public void ballVersusProjectileTest() {
    	try {
	    	Ball ball = mock(Ball.class);
	    	Projectile projectile = mock(Projectile.class);
	    	Player player = mock(Player.class);
	    	Ball[] ar = new Ball[2];
	    	ar[0] = ball;
	    	ar[1] = ball;
	    	when(ball.getSize()).thenReturn(MEDIUM_SIZE_BALL);
	    	when(projectile.getPlayer()).thenReturn(player);
	    	when(ball.split()).thenReturn(ar);
	    	when(level.getPowerUpManager()).thenReturn(mock(PowerUpManager.class));
	    	collisionResolver.ballVersusProjectile(ball, projectile);
	    	verify(ball).split();
	    	verify(level).removeBall(ball);
	    	verify(level).removeProjectile(projectile);
	    	verify(level).getPowerUpManager();
	    	verify(player).incrScore(anyInt());   
    	} catch (IllegalStateException e) {
    		e.printStackTrace();
    	} catch (MediaException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Test collision between projectile and ceiling.
     */
    @Test
    public void projectileCeilingTest() {
    	Projectile projectile = mock(Projectile.class);    	
    	when(level.isProjectileFreeze()).thenReturn(false);
    	collisionResolver.projectileVersusCeiling(projectile);
    	verify(level).removeProjectile(projectile);
    }
    
    /**
     * Test collision between ceiling and projectile while frozen.
     */
    @Test
    public void projectileCeilingFreezeTest() {
    	Projectile projectile = mock(Projectile.class);    	
    	when(level.isProjectileFreeze()).thenReturn(true);
    	collisionResolver.projectileVersusCeiling(projectile);
    	verify(projectile).setState(Projectile.State.FROZEN);
    }
}
