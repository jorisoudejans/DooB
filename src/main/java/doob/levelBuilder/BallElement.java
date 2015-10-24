package doob.levelBuilder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;



/**
 * This class represents the ball element, which can be added to a level in the levelbuilder.
 *
 */
public class BallElement extends DoobElement {
	
	public static final int MAX_BALLS = 3;

	private int size;
	private double speedX, speedY;
	
	/**
	 * Constructor.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @param size Size of the ball.
	 * @param speedX Horizontal speed of the ball.
	 * @param speedY Initial Vertical speed of the ball.
	 * @param gc The graphics object that can draw to the canvas.
	 */
	public BallElement(double x, double y, int size, int speedX, int speedY, GraphicsContext gc) {
		super(x, y, gc);
		this.size = size;
		this.speedX = speedX;
		this.speedY = speedY;
		image = new Image("/image/balls/ball" + size + ".png");
	}
	
	@Override
	public void drop(DragEvent event) {
		setX(event.getX() - image.getWidth() / 2);
		setY(event.getY() - image.getHeight() / 2);
		draw();
	}
	
	@Override
	public void draw() {
		gc.drawImage(image, x, y);
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.x && x < this.x + this.size
				&& y >= this.y && y < this.y + this.size);
	}

	public int getSize() {
		return size;
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}
}
