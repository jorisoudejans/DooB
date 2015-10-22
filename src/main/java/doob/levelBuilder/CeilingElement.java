package doob.levelBuilder;



/**
 * This class represents the ball element, which can be added to a level in the levelbuilder.
 *
 */
public class CeilingElement extends DoobElement {
	
	public static final int CEILING_WIDTH = 960;
	public static final int CEILING_HEIGHT = 100;
	public static final int CEILING_X = 170;
	
	private int width;
	private int height;
		
	/**
	 * Constructor.
	 * @param y Y coordinate.
	 */
	public CeilingElement(double y) {
		super(CEILING_X, y);
		width = CEILING_WIDTH;
		height = CEILING_HEIGHT;
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
