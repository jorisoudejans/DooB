package doob.model.powerup;

import doob.model.level.Level;
import doob.model.Player;

/**
 * Power-up that protects the player for one hit.
 */
@PowerUpChance(chance = PowerUp.CHANCE_PROTECT_ONCE)
public class ProtectOncePowerUp extends PowerUp {

    public static final int DURATION = 500;

    /**
     * Sets the player state to invulnerable.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        getPlayer().setState(Player.State.INVULNERABLE);
    }

    /**
     * Return to initial state.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {
        getPlayer().setState(Player.State.NORMAL);
    }

    /**
     * Get duration of power-up.
     * @return duration
     */
    @Override
    public int getDuration() {
        return DURATION;
    }

    /**
     * Path to shield-one icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/shield-one.png";
    }

}
