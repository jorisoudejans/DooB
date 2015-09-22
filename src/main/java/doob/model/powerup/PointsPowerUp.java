package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;
import doob.model.Player;

/**
 * Power-up that adds a life to the player.
 */
@PowerUpChance(chance = PowerUp.CHANCE_POINTS)
public class PointsPowerUp extends PowerUp {

    public static final int POINTS_TO_ADD = 500;

    /**
     * Adds one life to player.
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
    public int getTime() {
        return 0;
    }

    /**
     * Path to heart icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/star.png";
    }

    @Override
    public boolean collides(Collidable other) {
        return false;
    }
}
