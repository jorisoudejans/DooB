package doob.controller;


import org.hamcrest.core.IsNot;
import org.junit.Test;
import doob.model.Level;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests controller of game by using a junit rule.
 *
 * Created by hidde on 9/8/15.
 */

public class GameControllerTest extends ControllerTest {

    /**
     * Tests that our game controller exists in file.
     */
    //@Test
    //public void testBasic() {
    //    assertNotNull(getGameController());
    //}

    /**
     * Tests whether controller is initalized properly.
     */
    @Test
    public void testInitialize() {
        assertNotNull(getGameController().getLevel());
    }

    /**
     * Checks the game's initial state.
     */
    //@Test
    //public void testGetGameState() {
    //    assertEquals(GameController.GameState.RUNNING, getGameController().getGameState());
    //}

    @Test
    public void testCreateFreeze() throws InterruptedException{
        /*GameController gameController = getGameController();
        Level level = gameController.getLevel();
        level.update(); // times now differ
        assertThat(level.getTime(), IsNot.not(equalTo(level.getCurrentTime())));

        gameController.createFreeze();
        Thread.sleep(3000);
        assertEquals(level.getTime(), level.getCurrentTime());*/
    }

    private GameController getGameController() {
        return (GameController)getController("/fxml/game.fxml");
    }
}
