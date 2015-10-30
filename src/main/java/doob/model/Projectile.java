package doob.model;

import doob.model.level.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class all projectiles should extend.
 */
public abstract class Projectile implements Drawable, Collidable {

	private Image img;
	private Player player;
	private double xCoord;
	private double yCoord;

	private State state;

	/**
	 * Possible states of a projectile.
	 */
	public enum State {
		NORMAL,
		FROZEN
	}

	/**
	 * Abstract projectile.
	 * @param player the player that shot the projectile.
	 * @param x the x-location of the projectile.
	 * @param y the y-location of the projectile.
	 */
	public Projectile(Player player, double x, double y) {
		this.player = player;
		this.xCoord = x;
		this.yCoord = y;
		this.state = State.NORMAL;
	}

    /**
     * Draw projectile on canvas.
     * @param gc context to draw in.
     */
	public void draw(GraphicsContext gc) {
        gc.drawImage(img, xCoord, yCoord);
    }

	@Override
    public Rectangle getBounds() {
    	return new Rectangle(xCoord, yCoord, img != null ? img.getWidth() : 50, img != null ? img.getHeight() : 50);
    }
	
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public double getXCoord() {
		return xCoord;
	}

	public void setXCoord(double x) {
		this.xCoord = x;
	}
	
	public double getYCoord() {
		return yCoord;
	}

	public void setYCoord(double y) {
		this.yCoord = y;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Moves the projectile in y direction (up and down).
	 */
	public void move() {
		this.yCoord = yCoord - Level.getProjectileSpeed();
	}

	@Override
	public String toString() {
		return "Projectile{"
				+ "x=" + xCoord
				+ ", y=" + yCoord + '}';
	}
}
