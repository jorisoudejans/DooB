package doob.model.powerup;

import doob.model.level.Level;
import doob.model.Player;

/**
 * Represents a powerup that adds extra time.
 */
@PowerUpChance(chance = PowerUp.CHANCE_TIME)
public class TimePowerUp extends PowerUp {

    public static final int CYCLES_TO_ADD = 500;

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

    /**
     * Does nothing.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {

    }

    /**
     * Has no duration.
     * @return duration
     */
    @Override
    public int getDuration() {
        return 0;
    }

    /**
     * Get path to time icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/time.png";
    }


}
