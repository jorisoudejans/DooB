package doob.level;

import doob.model.Level;
import doob.model.Player;
import doob.model.Wall;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Class to test all resolving of collisions.
 */
public class CollisionResolverTest {

    private CollisionResolver collisionResolver;
    private Level level;

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

}
