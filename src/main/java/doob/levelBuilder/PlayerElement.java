package doob.levelBuilder;



/**
 * This class represents the player element, which can be added to a level in the levelbuilder.
 *
 */
public class PlayerElement extends DoobElement {
	
	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 72;
	
	private int width;
	private int height;
	
	/**
	 * Constructor.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public PlayerElement(double x, double y) {
		super(x, y);
		width = PLAYER_WIDTH;
		height = PLAYER_HEIGHT;
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
}
