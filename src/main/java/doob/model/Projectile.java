package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class all projectiles should extend.
 */
public abstract class Projectile implements Drawable, Collidable {

	private Image img;
	private double x;
	private double y;
	private double shootSpeed;
	
	/**
	 * Abstract projectile.
	 * @param x the x-location of the projectile.
	 * @param y the y-location of the projectile.
	 * @param shootSpeed the speed at which the y-location of the projectile changes.
	 */
	public Projectile(double x, double y, double shootSpeed) {
		this.x = x;
		this.y = y;
		this.shootSpeed = shootSpeed;
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
    
    public Rectangle getBounds() {
    	return new Rectangle(x, y, img.getWidth(), img.getHeight());
    }
	
	public double getShootSpeed() {
		return shootSpeed;
	}

	public void setShootSpeed(double shootSpeed) {
		this.shootSpeed = shootSpeed;
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
	
	/**
	 * Moves the projectile in y direction (up and down).
	 */
	public void move() {
		this.y = y - shootSpeed;
	}

	@Override
	public String toString() {
		return "Projectile{"
				+ "x=" + x
				+ ", y=" + y
				+ ", shootSpeed=" + shootSpeed + '}';
	}
}
