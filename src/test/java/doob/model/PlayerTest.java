package doob.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * Testing Player on implementing Drawable.
 *
 * Created by hidde on 9/8/15.
 */
public class PlayerTest extends DrawableTest {

    private Player player;

    private final int PLAYER_INITIAL_X = 300;
    private final int PLAYER_INITIAL_Y = 300;

    /**
     * Set up test cases, assign drawable to a Player.
     */
    @Override
    public void setUp() {

        this.player = new Player(PLAYER_INITIAL_X, PLAYER_INITIAL_Y, 100, 100, null, null, null);
        this.drawable = this.player; // init drawable for superclass tests
    }

    /**
     * Tests the construction of player.
     */
    @Test
    public void testConstructor() {

        this.player = new Player(PLAYER_INITIAL_X, PLAYER_INITIAL_Y, 100, 100, null, null, null);
        assertNotNull(this.player);

    }

    /**
     * Player should move accordingly. Speed is 0
     */
    @Override
    public void testMove() {
        super.testMove(); // calls move() on drawable
        assertEquals(PLAYER_INITIAL_X, player.getX()); // player hasn't moved from starting position
        assertEquals(PLAYER_INITIAL_Y, player.getY());
    }

    /**
     * Player's Y value should never be influenced.
     */
    @Test
    public void testMoveNotY() {
        player.setSpeed(50);
        player.move();
        assertEquals(PLAYER_INITIAL_Y, player.getY());
    }

    /**
     * Player move to the right.
     */
    @Test
    public void testMoveRight() {
        player.setSpeed(10);
        player.move();
        assertEquals(310, player.getX());
    }

    /**
     * Player move to the left.
     */
    @Test
    public void testMoveLeft() {
        player.setSpeed(-10);
        player.move();
        assertEquals(290, player.getX());
    }

    /**
     * Player move twice.
     */
    @Test
    public void testMoveTwice() {
        player.setSpeed(10);
        player.move();
        player.move();
        assertEquals(320, player.getX());
    }

    /**
     * Player move multiple times.
     */
    @Test
    public void testMoveMultiple() {
        player.setSpeed(10);
        player.move();
        player.move();
        player.move();
        player.move();
        player.move();
        assertEquals(350, player.getX());
    }

    /**
     * Player move varying speed.
     */
    @Test
    public void testMoveVaryingSpeed() {
        player.setSpeed(10);
        player.move();
        player.move();
        player.move();
        player.setSpeed(30);
        player.move();
        player.move();
        assertEquals(390, player.getX());
    }

    /**
     * Player move left-right-left
     */
    @Test
    public void testMoveLeftRight() {
        player.setSpeed(-20); // left
        player.move();
        player.move();
        player.setSpeed(30); // right
        player.move();
        player.move();
        player.setSpeed(-10); // left
        player.move();
        assertEquals(310, player.getX());
    }


}
