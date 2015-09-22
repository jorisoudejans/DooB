package doob.model.powerup;

import doob.model.Level;

public abstract class PowerUp {

	private int endTime;

	/**
	 * Construct powerup with endtime
	 * @param timeOfDisappear
	 */
	public PowerUp(int timeOfDisappear) {
		this.endTime = timeOfDisappear;
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
