package doob.model.powerup;

import doob.model.Level;

public abstract class PowerUp {

	private int endTime;

	/**
	 * Construct powerup with current game time. Calculates the disappear time
	 * @param currentTime current time of game
	 */
	public PowerUp(int currentTime) {
		this.endTime = currentTime + this.getTime();
	}

	/**
	 * Method that is called when powerup is activated.
	 * @param level the level the powerup is in
	 */
	public void onActivate(Level level) {
		// custom
	}

	/**
	 * Method that is called when powerup is deactivated.
	 * @param level the level the powerup is in
	 */
	public void onDeactivate(Level level) {
		// custom
	}

	/**
	 * Gives the time the powerup is working
	 * @return time the powerup is working
	 */
	public int getTime() {
		return 0;
	}

	/**
	 * Gives the chance this powerup will be dropped this cycle.
	 * @return number between 0 and 1
	 */
	public double getChance() {
		return 0.0;
	}

	/**
	 * Returns the end time in level
	 * @return end time
	 */
	public double getEndTime() {
		return this.endTime;
	}

	/**
	 * Sets end time based from level
	 * @param duration the end time
	 */
	public void setEndTime(int duration) {
		this.endTime = duration;
	}

}
