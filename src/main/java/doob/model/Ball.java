package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

public class Ball implements Collidable, Drawable {

	private double x;
	private double y;
	private double speedX;
	private double speedY;
	private double size;
	private int bounceSpeed;
	private int splitBounce;
	private int ballSpeed;

	private int canvasWidth;
	private int canvasHeight;
	
	/**
	 * Constructor.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param speedX x-axis speed
	 * @param speedY y-axis speed
	 * @param size the size of the ball
	 */
	public Ball(double x, double y, double speedX, double speedY, double size) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.size = size;
		this.ballSpeed = 3;
		this.bounceSpeed = -8;
		this.splitBounce = -10;
	}

	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.fillOval(this.getX(), this.getY(), this.getSize(), this.getSize());
	}

	/**
	 * Move the ball in a physics simulation.
	 */
	public void move() {
		moveHorizontally();
		/*if (x + size >= canvasWidth) {
			setSpeedX(-ballSpeed);
		} else if (x <= 0) {
			setSpeedX(ballSpeed);
		}*/
		moveVertically();
		incrSpeedY(0.5);
		/*if (y + size > canvasHeight) {
			setSpeedY(getBounceSpeed());
		}*/
	}
	/**
	 * Split function which handles the creation of new balls after one is hit.
	 * @return A list of new balls.
	 */
	public Ball[] split() {
		Ball ball1 = new Ball(this.x, this.y, ballSpeed, splitBounce, this.size / 2);
		Ball ball2 = new Ball(this.x, this.y, -ballSpeed, splitBounce, this.size / 2);
		Ball[] res =  new Ball[2];
		res[0] = ball1;
		res[1] = ball2;
		return res;		
	}

	public boolean collides(Collidable other) {
		return false;
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

	public Circle getBounds() {
		return new Circle(x + size / 2, y + size / 2, size / 2);
	}
	
	public double getBounceSpeed() {
		if (size == 96) {
			return -20;
		} else if (size == 48) {
			return -18;
		} else if (size == 24) {
			return -16;
		} else if (size == 12) {
			return -14;
		}
		return -12;
	}

	public int getsplitBounce() {
		return splitBounce;
	}

	public void setsplitBounce(int splitBounce) {
		this.splitBounce = splitBounce;
	}

	public int getBallSpeed() {
		return ballSpeed;
	}

	public void setBallSpeed(int ballSpeed) {
		this.ballSpeed = ballSpeed;
	}

	public void setBounceSpeed(int bounceSpeed) {
		this.bounceSpeed = bounceSpeed;
	}
}
