package doob.model;

import javafx.scene.canvas.GraphicsContext;

public class Ball extends Sprite {

	private double x;
	private double y;
	private double speedX;
	private double speedY;
	private double size;

	public Ball(double x, double y, double speedX, double speedY, double size) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.size = size;
	}

	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.fillOval(this.getX(), this.getY(), this.getSize(), this.getSize());
	}
	
	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
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
	
	public void moveHorizontally() {
		this.x = x + speedX;
	}
	
	public void moveVertically() {
		this.y = y + speedY;
	}
	
	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	
	public void incrSpeedX(double speedDX) {
		this.speedX = speedX + speedDX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	
	public void incrSpeedY(double speedDY) {
		this.speedY = speedY + speedDY;
	}

	//public Rectangle getBounds() {
		//return new Rectangle(x, y, size, size);
	//}
}
