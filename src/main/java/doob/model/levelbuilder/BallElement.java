package doob.model.levelbuilder;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.shape.Rectangle;




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
	 */
	public BallElement(double x, double y, int size, int speedX, int speedY) {
		super(x, y);
		this.size = size;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	@Override
	public void image() {
		this.image = new Image("/image/balls/ball" + size + ".png");
	}
	
	@Override
	public void drop(DragEvent event) {
		setXCoord(event.getX() - image.getWidth() / 2);
		setYCoord(event.getY() - image.getHeight() / 2);
		change();
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.xCoord
				&& x < this.xCoord + this.size
				&& y >= this.yCoord 
				&& y < this.yCoord + this.size);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(xCoord, yCoord, size, size);
	}

	/**
	 * Return the amount of elements of this class in the given list.
	 * @param elementList The list where to check the amount of elements from.
	 * @return The amount of elements of this class in the list.
	 */
	public static int getAmount(ArrayList<DoobElement> elementList) {
		int res = 0;
		for (DoobElement el : elementList) {
			if (el instanceof BallElement) {
				res++;
			}
		}
		return res;
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
