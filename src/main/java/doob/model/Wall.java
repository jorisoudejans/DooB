package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class for creating walls in the game.
 *
 */
public class Wall implements Collidable, Drawable {
	
	private int x, y, width, height;
	private boolean moveable;
	private int endx, endy;
	private int duration;
	private int speed;

	private boolean open;
	private String condition;	//TODO Use an ENUM for this
	private Rectangle r;
	
	/**
	 * Constructor for a simple wall which is not moveable.
	 * @param x the top left
	 * @param y	the top right
	 * @param width the width
	 * @param height the height
	 */
	public Wall(int x, int y, int width, int height) {
		this.open = false;
		this.moveable = false;
		r = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width; 
		this.height = height;
	}

	/**
	 * Constructor for an advanced wall which is able to move.
	 * @param x The top left of the wall
	 * @param y The top right of the wall
	 * @param width The width of the wall
	 * @param height The height of the wall
	 * @param endx The x end-position of the wall
	 * @param endy The y end-position of the wall
	 * @param duration How long does is take to move the wall
	 * @param condition When does the wall have to move
	 */
	public Wall(int x, int y, int width, int height, int endx, int endy, 
			int duration, int speed, String condition) {
		this.moveable = true;
		r = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.endx = endx;
		this.endy = endy;
		this.duration = duration;
		this.speed = speed;
		this.condition = condition;
	}
	
	/**
	 * Return the bounds of the wall.
	 * @return rectangle with bounds.
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * Collision detection.
	 * @param other other collideable.
	 * @return boolean if the collideables collide.
	 */
	public boolean collides(Collidable other) {
		if (other instanceof Projectile) {
			Projectile p = (Projectile) other;
			return p.getBounds().intersects(x, y, width, height);
		}
		return false;
	}
	
	/**
     * Draw wall on canvas.
     * @param gc context to draw in.
     */
	public void draw(GraphicsContext gc) {
        if (open) {
        	gc.setFill(Color.GRAY);
        	gc.fillRect(x, y, width, height - 250);
        } else {
        	gc.fillRect(x, y, width, height);
        }        
        gc.setFill(Color.BLACK);
    }

	/**
	 * Move the wall in the right direction.
	 */
	@Override
	public void move() {
		if (duration > 0) {
			if (x < endx) {
				x = x + speed;
			} else {
				x = x - speed;
			}
			if (y < endy) {
				y = y + speed;
			} else {
				y = y - speed;
			}
			duration--;
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		this.r = r;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true; }
		if (obj == null) {
			return false; }
		if (getClass() != obj.getClass()) {
			return false; }
		Wall other = (Wall) obj;
		if (speed != other.speed) {
			return false; }
		if (width != other.width) {
			return false; }
		if (x != other.x) {
			return false; }
		if (y != other.y) {
			return false; }
		if (moveable != other.moveable) {
			return false; }
		if (!moveable) {
			return true; }
		if (!condition.equals(other.condition)) {
			return false; }
		if (duration != other.duration) {
			return false; }
		if (endx != other.endx) {
			return false; }
		if (endy != other.endy) {
			return false; }
		if (height != other.height) {
			return false; }
		return true;
	}	
}
