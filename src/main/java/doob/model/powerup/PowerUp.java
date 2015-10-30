package doob.model.powerup;

import doob.model.Collidable;
import doob.model.Player;
import doob.model.level.Level;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Abstract Power-up class to be extended by every possible power-up.
 */
public abstract class PowerUp implements Collidable {

	// Chances
	public static final float CHANCE_TIME = 0.02f;
	public static final float CHANCE_LIFE = 0.01f;
	public static final float CHANCE_POINTS = 0.02f;
	public static final float CHANCE_PROTECT_ONCE = 0.02f;
	public static final float CHANCE_FREEZE_BALLS = 0.02f;
	public static final float CHANCE_PLAYER_SPEED = 0.02f;
	public static final float CHANCE_PROJECTILE_SPEED = 0.02f;
	public static final float CHANCE_PROJECTILE_FREEZE = 0.02f;

	public static final int DEFAULT_WAIT_CYCLES = 500;

	public static final int POWERUP_COLLISION_SIZE = 30;

	private int currentWaitTime;
	private int activeTime;
	private double locationX;
	private double locationY;
	private Image spriteImage;

	private Player player;

	/**
	 * Construct power-up with current game time. Calculates the disappear time.
	 */
	public PowerUp() {
		this.activeTime = this.getDuration();
		this.currentWaitTime = DEFAULT_WAIT_CYCLES;
	}

	/**
	 * Method that is called when power-up is activated.
	 * @param level the level the power-up is in.
	 * @param player the player that picked up the power-up.
	 */
	public void onActivate(Level level, Player player) {
		this.player = player;
	}

	/**
	 * Method that is called when power-up is deactivated.
	 * @param level the level the power-up is in.
	 */
	public abstract void onDeactivate(Level level);

	/**
	 * Gives the time the power-up is working.
	 * @return time the power-up is working.
	 */
	public abstract int getDuration();

	/**
	 * Path to the visual.
	 * @return path
	 */
	public abstract String spritePath();

	/**
	 * Returns the end time in level.
	 * @return end time.
	 */
	public double getActiveTime() {
		return this.activeTime;
	}

	/**
	 * Sets end time based from level.
	 * @param duration the end time.
	 */
	public void setActiveTime(int duration) {
		this.activeTime = duration;
	}

	public int getCurrentWaitTime() {
		return currentWaitTime;
	}

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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getLocationX() {

		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	/**
	 * GameUI tick on power-up. Decrease time left.
	 */
	public void tickActive() {
		this.activeTime--;
	}

	/**
	 * GameUI tick on power-up. Decrease time left.
	 */
	public void tickWait() {
		this.currentWaitTime--;
	}

	/**
	 * Returns bounds.
	 * @return shape with bounds
	 */
	@Override
	public Shape getBounds() {
		return new Rectangle(locationX, locationY, POWERUP_COLLISION_SIZE, POWERUP_COLLISION_SIZE);
	}
}
