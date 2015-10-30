package doob.model.powerup;

import doob.model.level.Level;
import doob.model.Player;

/**
 * Power-up that speeds up all projectiles for a short period of time.
 */
@PowerUpChance(chance = PowerUp.CHANCE_PROJECTILE_SPEED)
public class ProjectileSpeedPowerUp extends PowerUp {

    public static final int DURATION = 500;
    public static final int SPEED_FACTOR = 2;

    /**
     * Speeds up all projectiles.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        Level.setProjectileSpeed(Level.PROJECTILE_START_SPEED * SPEED_FACTOR);
    }

    /**
     * Return to normal state.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {
        Level.setProjectileSpeed(Level.PROJECTILE_START_SPEED);
    }

    /**
     * Get duration.
     * @return DURATION
     */
    @Override
    public int getDuration() {
        return DURATION;
    }

    /**
     * Path to fast-up icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/fast-up.png";
    }

}
