package doob.game.model;

import doob.App;
import doob.game.GameUI;
import doob.model.Player;
import org.junit.Test;
import static org.mockito.Mockito.*;
import doob.model.level.Level;

import java.util.ArrayList;
import java.util.Observable;


/**
 * Created by Shane on 30-10-2015.
 */
public class GameTest {

    @Test
    public void testPausePlay() {
        Game game = mock(Game.class, CALLS_REAL_METHODS);
        GameUI ui = mock(GameUI.class);
        Level level = mock(Level.class);

        game.setUI(ui);
        game.setLevel(level);
        game.setRunning(true);

        game.pausePlay();
        verify(ui).setPlayPauseButton("Play");

        game.pausePlay();
        verify(ui).setPlayPauseButton("Pause");
    }

    @Test
    public void testBackToMenu() {
        Game game = mock(Game.class);
        doCallRealMethod().when(game).backToMenu();

        Level level = mock(Level.class);

        game.setLevel(level);

        game.backToMenu();
        verify(level).stopTimer();
    }

    @Test
    public void testUpdate() {
        Game game = mock(Game.class);
        Observable ob = mock(Observable.class);

        Level.Event ev1 = Level.Event.ZERO_LIVES;
        doCallRealMethod().when(game).update(ob, ev1);
        game.update(ob, ev1);
        verify(game).onZeroLives();

        Level.Event ev2 = Level.Event.ALL_BALLS_GONE;
        doCallRealMethod().when(game).update(ob, ev2);
        game.update(ob, ev2);
        verify(game).onAllBallsGone();

        Level.Event ev3 = Level.Event.LOST_LIFE;
        doCallRealMethod().when(game).update(ob, ev3);
        game.update(ob, ev3);
        verify(game).onLostLife();
    }

    @Test
    public void testUpdateProgressBar() {
        Game game = mock(Game.class);
        doCallRealMethod().when(game).updateProgressBar();


        Player player = mock(Player.class);
        when(player.isAlive()).thenReturn(true);

        ArrayList<Player> pl = new ArrayList<Player>();
        pl.add(player);

        Level level = mock(Level.class);
        when(level.getCurrentTime()).thenReturn((double) 0);
        when(level.getTime()).thenReturn(100);
        when(level.getPlayers()).thenReturn(pl);

        GameUI ui = mock(GameUI.class);

        doCallRealMethod().when(game).setUI(ui);
        doCallRealMethod().when(game).setLevel(level);
        game.setUI(ui);
        game.setLevel(level);

        game.updateProgressBar();

        verify(player).die();
    }
}
