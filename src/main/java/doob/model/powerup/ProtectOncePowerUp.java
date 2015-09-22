package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;
import doob.model.Player;

/**
 * Power-up that protects the player for one hit.
 */
@PowerUpChance(chance = PowerUp.CHANCE_PROTECT_ONCE)
public class ProtectOncePowerUp extends PowerUp {

    public static final int DURATION = 500;

    /**
     * Adds one life to player.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        getPlayer().setState(Player.State.INVULNERABLE);
    }

    /**
     * Does nothing.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {
        getPlayer().setState(Player.State.NORMAL);
    }

    /**
     * Has no duration.
     * @return 0
     */
    @Override
    public int getDuration() {
        return DURATION;
    }

    /**
     * Path to heart icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/shield-one.png";
    }

    @Override
    public boolean collides(Collidable other) {
        return false;
    }
}
