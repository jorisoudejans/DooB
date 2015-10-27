package doob.model.levelbuilder;

import doob.controller.LevelBuilderController;
import javafx.scene.input.MouseEvent;



/**
 * This class represents the wall element, which can be added to a level in the levelbuilder.
 *
 */
public class WallElement extends DoobElement {
	
	public static final int WALL_WIDTH = 100;
	public static final int WALL_HEIGHT = 650;
	public static final int WALL_Y = 0;
	public static final int MAX_WALLS = 2;
	
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
	
	@Override
	public void handleDrag(MouseEvent event) {
		setX(event.getSceneX() - LevelBuilderController.PANE_X - WallElement.WALL_WIDTH / 2);
	}

	public int getWidth() {
		return width;
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.x && x < this.x + this.width
				&& y >= this.y && y < this.y + this.height);
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
