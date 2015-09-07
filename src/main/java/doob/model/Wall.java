package doob.model;

import javafx.scene.shape.Rectangle;

public class Wall implements Collidable{
	
	private int x, y, width, height;
	private Rectangle r;
	
	public Wall (int x, int y, int width, int height) {
		r = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle getBounds() {
		return this.r;
	}

	public boolean collides(Collidable other) {
		// TODO Auto-generated method stub
		return false;
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
	
}
