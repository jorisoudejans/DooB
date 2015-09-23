package doob.model;

import javafx.scene.canvas.GraphicsContext;
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
			int duration, String condition) {
		this.moveable = true;
		r = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.endx = endx;
		this.endy = endy;
		this.duration = duration;
		this.condition = condition;
	}
	
	public Rectangle getBounds() {
		return this.r;
	}
	
	/**
	 * Collision detection.
	 * @param other other collideable.
	 * @return boolean if the collideables collide.
	 */
	public boolean collides(Collidable other) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
     * Draw wall on canvas.
     * @param gc context to draw in.
     */
	public void draw(GraphicsContext gc) {
        gc.fillRect(x, y, width, height);
    }


	@Override
	public void move() {
		// TODO Auto-generated method stub
		
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
	
}
