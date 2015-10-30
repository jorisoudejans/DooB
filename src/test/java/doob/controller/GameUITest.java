package doob.controller;


import doob.game.GameUI;
import org.junit.Test;

/**
 * Tests controller of game by using a junit rule.
 *
 * Created by hidde on 9/8/15.
 */

public class GameUITest extends ControllerTest {

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
    //@Test
    //public void testInitialize() {
    //    assertNotNull(getGameController().getLevel());
    //}

    /**
     * Checks the game's initial state.
     */
    //@Test
    //public void testGetGameState() {
    //    assertEquals(GameUI.GameState.RUNNING, getGameController().getGameState());
    //}

    @Test
    public void testCreateFreeze() throws InterruptedException{
        /*GameUI gameController = getGameController();
        Level level = gameController.getLevel();
        level.update(); // times now differ
        assertThat(level.getDuration(), IsNot.not(equalTo(level.getCurrentTime())));

        gameController.createFreeze();
        Thread.sleep(3000);
        assertEquals(level.getDuration(), level.getCurrentTime());*/
    }

    private GameUI getGameController() {
        return (GameUI)getController("/FXML/game.fxml");
    }
}
