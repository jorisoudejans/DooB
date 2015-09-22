package doob.model.powerup;

import doob.model.Level;

/**
 * Represents a powerup that adds extra time
 *
 * Created by hidde on 9/10/15.
 */
public class TimePowerUp extends PowerUp {


    /**
     * Construct powerup with endtime
     *
     * @param timeOfDisappear
     */
    public TimePowerUp(int timeOfDisappear) {
        super(timeOfDisappear);
    }

    /**
     * Powerup works one second.
     * @return duration
     */
    @Override
    public int getTime() {
        return 1;
    }

    /**
     * Adds 5 seconds to level.
     * @param level the level the powerup is in
     */
    @Override
    public void onActivate(Level level) {
        super.onActivate(level);
        level.setCurrentTime( level.getCurrentTime() + 500 );
    }
}
