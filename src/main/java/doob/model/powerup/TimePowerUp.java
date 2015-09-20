package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;

/**
 * Represents a powerup that adds extra time
 *
 * Created by hidde on 9/10/15.
 */
@PowerUpChance(chance = 0.5)
public class TimePowerUp extends PowerUp {

    /**
     * Powerup works one second.
     * @return duration
     */
    @Override
    public int getTime() {
        return 1;
    }

    @Override
    public String spritePath() {
        return "/image/powerup/time.png";
    }

    /**
     * Adds 5 seconds to level.
     * @param level the level the powerup is in
     */
    @Override
    public void onActivate(Level level) {
        super.onActivate(level);
        level.setCurrentTime( Math.min(level.getCurrentTime() + 500, Level.TIME ));
    }

    @Override
    public boolean collides(Collidable other) {
        return false;
    }
}
