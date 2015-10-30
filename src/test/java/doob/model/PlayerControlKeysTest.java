package doob.model;

import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Tests static class Player.ControlKeys
 * Created by hidde on 10/16/15.
 */
public class PlayerControlKeysTest {

    private KeyCode rightKey = KeyCode.RIGHT;
    private KeyCode leftKey = KeyCode.LEFT;
    private KeyCode shootKey = KeyCode.SPACE;

    private Player.ControlKeys keys;

    /**
     * Sets up controlkeys and mocks.
     */
    @Before
    public void setUp() {
        keys = new Player.ControlKeys(leftKey, rightKey, shootKey);
    }

    /**
     * Tests right action.
     */
    @Test
    public void testActionRight() {
        assertEquals(Player.ControlKeys.Action.RIGHT, keys.determineAction(rightKey));
    }

    /**
     * Tests left action.
     */
    @Test
    public void testActionLeft() {
        assertEquals(Player.ControlKeys.Action.LEFT, keys.determineAction(leftKey));
    }

    /**
     * Tests shoot action.
     */
    @Test
    public void testActionShoot() {
        assertEquals(Player.ControlKeys.Action.SHOOT, keys.determineAction(shootKey));
    }

    /**
     * Tests none action.
     */
    @Test
    public void testActionNone() {
        assertEquals(Player.ControlKeys.Action.NONE, keys.determineAction(null));
    }

    /**
     * Tests if leftKey is move key
     */
    @Test
    public void testIsMoveKey() {
        assertTrue(keys.isMoveKey(leftKey));
    }

}
