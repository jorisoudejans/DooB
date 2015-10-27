package doob.model.powerup;

import doob.model.level.Level;
import doob.model.Player;

/**
 * Power-up that adds a point to the player.
 */
@PowerUpChance(chance = PowerUp.CHANCE_POINTS)
public class PointsPowerUp extends PowerUp {

    public static final int POINTS_TO_ADD = 500;

    /**
     * Adds points to player.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        player.setScore(player.getScore() + POINTS_TO_ADD);
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
     * @return 0
     */
    @Override
    public int getDuration() {
        return 0;
    }

    /**
     * Path to star icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/star.png";
    }

}
