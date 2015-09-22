package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;

/**
 * Represents a powerup that adds extra time.
 */
@PowerUpChance(chance = PowerUp.CHANCE_TIME)
public class TimePowerUp extends PowerUp {

    public static final int CYCLES_TO_ADD = 500;

    /**
     * Powerup works one second.
     * @return duration
     */
    @Override
    public int getTime() {
        return 1;
    }

    @Override
    public String spritePath() {
        return "/image/powerup/time.png";
    }

    /**
     * Adds cycles to level.
     * @param level the level the powerup is in
     */
    @Override
    public void onActivate(Level level) {
        level.setCurrentTime(Math.min(level.getCurrentTime() + CYCLES_TO_ADD, Level.TIME));
    }

    @Override
    public void onDeactivate(Level level) {
        // empty
    }

    @Override
    public boolean collides(Collidable other) {
        return false;
    }
}
