package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;
import doob.model.Player;

/**
 * Power-up that adds a life to the player.
 */
@PowerUpChance(chance = PowerUp.CHANCE_LIFE)
public class LifePowerUp extends PowerUp {

	public static final int MAX_LIVES = 7;
	
    /**
     * Adds one life to player.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        if (player.getLives() < MAX_LIVES) {
        	player.setLives(player.getLives() + 1);
        }
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
     * Path to heart icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/life.png";
    }

}
