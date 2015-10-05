package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;
import doob.model.Player;

/**
 * Power-up that freezes all balls for a short period of time.
 */
@PowerUpChance(chance = PowerUp.CHANCE_PROJECTILE_SPEED)
public class ProjectileSpeedPowerUp extends PowerUp {

    public static final int DURATION = 500;
    public static final int SPEED_FACTOR = 2;

    /**
     * Adds one life to player.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        Level.setProjectileSpeed(Level.PROJECTILE_START_SPEED * SPEED_FACTOR);
    }

    /**
     * Does nothing.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {
        Level.setProjectileSpeed(Level.PROJECTILE_START_SPEED);
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
        return "/image/powerup/fast-up.png";
    }

}
