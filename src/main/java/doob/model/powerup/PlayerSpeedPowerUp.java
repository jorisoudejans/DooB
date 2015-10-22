package doob.model.powerup;

import doob.model.Level;
import doob.model.Player;

/**
 * Power-up that speeds the player up for a short period of time.
 */
@PowerUpChance(chance = PowerUp.CHANCE_PLAYER_SPEED)
public class PlayerSpeedPowerUp extends PowerUp {

    public static final int DURATION = 500;
    public static final int SPEED_FACTOR = 2;

    /**
     * Speeds the player up with a factor of SPEED_FACTOR.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        player.setMoveSpeed(Player.START_SPEED * SPEED_FACTOR);
    }

    /**
     * Return to initial state.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {
        getPlayer().setMoveSpeed(Player.START_SPEED);
    }

    /**
     * Get duration of speed powerup.
     * @return DURATION
     */
    @Override
    public int getDuration() {
        return DURATION;
    }

    /**
     * Path to fast-right icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/fast-right.png";
    }

}
