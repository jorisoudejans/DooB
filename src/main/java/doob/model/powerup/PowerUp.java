package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Level;
import javafx.scene.image.Image;

public abstract class PowerUp implements Collidable {

	private int waitTime;
	private int activeTime;
	private double locationX;
	private double locationY;
	private Image spriteImage;

	public Image getSpriteImage() {
		return spriteImage;
	}

	public void setSpriteImage(Image spriteImage) {
		this.spriteImage = spriteImage;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public double getLocationX() {

		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	/**
	 * Construct powerup with current game time. Calculates the disappear time
	 */
	public PowerUp() {
		this.activeTime = this.getTime();
		this.waitTime = 500; //  seconds?
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
	public abstract int getTime();

	/**
	 * Path to the visual
	 * @return path
	 */
	public abstract String spritePath();

	/**
	 * Returns the end time in level
	 * @return end time
	 */
	public double getActiveTime() {
		return this.activeTime;
	}

	/**
	 * Sets end time based from level
	 * @param duration the end time
	 */
	public void setActiveTime(int duration) {
		this.activeTime = duration;
	}

	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * Game tick on powerup. Decrease time left
	 */
	public void tickActive() {
		this.activeTime--;
	}

	/**
	 * Game tick on powerup. Decrease time left
	 */
	public void tickWait() {
		this.waitTime--;
	}

}
