package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Abstract class all projectiles should extend.
 */
public abstract class Projectile implements Drawable, Collidable {

	private Image img;
	private double x;
	private double y;

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
	 * @param x the x-location of the projectile.
	 * @param y the y-location of the projectile.
	 */
	public Projectile(double x, double y) {
		this.x = x;
		this.y = y;
		this.state = State.NORMAL;
	}

    /**
     * Draw projectile on canvas.
     * @param gc context to draw in.
     */
	public void draw(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    /**
     * Checks if projectile collides with other object.
     * @param other object to be compared to.
     * @return true if the two objects collide.
     */
    public boolean collides(Collidable other) {
        if (other instanceof Ball) {
            Ball b = (Ball) other;
			if (img != null) {
				return b.getBounds().intersects(x, y, img.getWidth(), img.getHeight());
			}
			return b.getBounds().intersects(x, y, 20, 20); // for testing
		}
        return false;
    }

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Moves the projectile in y direction (up and down).
	 */
	public void move() {
		this.y = y - Level.getProjectileSpeed();
	}

	@Override
	public String toString() {
		return "Projectile{"
				+ "x=" + x
				+ ", y=" + y + '}';
	}
}
