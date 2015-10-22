package doob.levelBuilder;



/**
 * This class represents the ball element, which can be added to a level in the levelbuilder.
 *
 */
public class WallElement extends DoobElement {
	
	public static final int WALL_WIDTH = 100;
	public static final int WALL_HEIGHT = 650;
	public static final int WALL_Y = 0;
	
	private int width;
	private int height;
		
	/**
	 * Constructor.
	 * @param x X coordinate.
	 */
	public WallElement(double x) {
		super(x, WALL_Y);
		width = WALL_WIDTH;
		height = WALL_HEIGHT;
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
