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
	private double shootSpeed;
	
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
            return b.getBounds().intersects(x, y, img.getWidth(), img.getHeight());
        }
        return false;
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

	public void move() {
		this.y = y - shootSpeed;
	}

}
