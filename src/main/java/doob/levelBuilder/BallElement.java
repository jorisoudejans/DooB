package doob.levelBuilder;

import javafx.scene.input.MouseEvent;



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
	public void handleDrag(MouseEvent event) {
		setX(event.getSceneX() - LevelBuilderController.PANE_X - size / 2);
		setY(event.getSceneY() - LevelBuilderController.PANE_Y - size / 2);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
}
