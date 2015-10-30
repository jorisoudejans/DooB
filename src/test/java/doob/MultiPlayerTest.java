package doob;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;

import doob.model.level.Level;
import doob.model.Player;


/**
 * Class to test multiplayer functionality.
 * @author Jasper
 *
 */
public class MultiPlayerTest {

	public static final int MAX_LIVES = 5;
	
	/**
	 * Test whether a player is dead when he loses 5 lives and the other player is still alive.
	 */
	@Test
	public void multiPlayerDieTest() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(0, 0, 0, 0, null, null, null));
		players.add(new Player(0, 0, 0, 0, null, null, null));
		Level level = mock(Level.class);
		when(level.getPlayers()).thenReturn(players);
		for (int i = 0; i < MAX_LIVES; i++) {
			level.getPlayers().get(0).die();
		}
		assertFalse(level.getPlayers().get(0).isAlive());
		assertTrue(level.getPlayers().get(1).isAlive());
	}

}
