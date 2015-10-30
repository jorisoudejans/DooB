package doob.controller;

import doob.model.level.Level;
import doob.model.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

/**
 * Class to manage level
 *
 * Created by hidde on 10/20/15.
 */
public class LevelController implements Observer, EventHandler<KeyEvent> {

    private Level level;

    /**
     * Construct a new levelcontroller.
     * @param level the level
     */
    public LevelController(Level level) {
        this.level = level;
        this.level.addObserver(this);
    }

    /**
     * Observes changes.
     * @param o level object
     * @param arg event
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Handler for key presses.
     * @param key the key that was pressed
     */
    public void handle(KeyEvent key) {
        for (Player p : level.getPlayers()) {
            if (p.isAlive()) {
                Player.ControlKeys.Action action = p.getControlKeys().determineAction(key.getCode());
                switch (action) {
                    case RIGHT:
                        p.setSpeed(p.getMoveSpeed());
                        break;
                    case LEFT:
                        p.setSpeed(-p.getMoveSpeed());
                        break;
                    case SHOOT:
                        level.shoot(p);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
