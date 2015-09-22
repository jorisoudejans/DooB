package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;
import doob.model.Player;

/**
 * Power-up that freezes all balls for a short period of time.
 */
@PowerUpChance(chance = PowerUp.CHANCE_PROJECTILE_FREEZE)
public class ProjectileFreezePowerUp extends PowerUp {

    public static final int DURATION = 500;

    /**
     * Adds one life to player.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        level.setState(Level.State.PROJECTILES_FREEZE);
    }

    /**
     * Does nothing.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {
        level.setState(Level.State.NORMAL);
    }

    /**
     * Has no duration.
     * @return duration
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
        return "/image/powerup/straight.png";
    }

    @Override
    public boolean collides(Collidable other) {
        return false;
    }
}
