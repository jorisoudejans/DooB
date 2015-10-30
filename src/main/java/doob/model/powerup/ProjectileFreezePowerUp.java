package doob.model.powerup;

import doob.model.level.Level;
import doob.model.Player;

/**
 * Power-up that freezes projectiles when
 * they hit the ceiling for a short period of time.
 */
@PowerUpChance(chance = PowerUp.CHANCE_PROJECTILE_FREEZE)
public class ProjectileFreezePowerUp extends PowerUp {

    public static final int DURATION = 500;

    /**
     * Freezes all projectiles.
     * @param level the level the power-up is in.
     * @param player the player that picked up the power-up.
     */
    @Override
    public void onActivate(Level level, Player player) {
        super.onActivate(level, player);
        level.setProjectileFreeze(true);
    }

    /**
     * Return to normal state.
     * @param level the level the power-up is in.
     */
    @Override
    public void onDeactivate(Level level) {
        level.setProjectileFreeze(false);
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
     * Path to straight up icon.
     * @return path
     */
    @Override
    public String spritePath() {
        return "/image/powerup/straight.png";
    }

}
