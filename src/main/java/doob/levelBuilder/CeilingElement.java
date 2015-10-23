package doob.levelBuilder;

import javafx.scene.input.MouseEvent;



/**
 * This class represents the ceiling element, which can be added to a level in the levelbuilder.
 *
 */
public class CeilingElement extends DoobElement {
	
	public static final int CEILING_WIDTH = 960;
	public static final int CEILING_HEIGHT = 100;
	public static final int CEILING_X = 0;
	
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
	
	@Override
	public void handleDrag(MouseEvent event) {
		setY(event.getSceneY() - LevelBuilderController.PANE_Y - CeilingElement.CEILING_HEIGHT / 2);
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
