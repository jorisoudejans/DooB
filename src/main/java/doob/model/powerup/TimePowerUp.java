package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;
import doob.model.Player;

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
    public int getDuration() {
        return 0;
    }

    @Override
    public String spritePath() {
        return "/image/powerup/time.png";
    }

    /**
     * Adds cycles to level.
     * @param level the level the powerup is in
     * @param player the player that picked up the power-up
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        level.setCurrentTime(Math.min(level.getCurrentTime() + CYCLES_TO_ADD, level.getTime()));
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
