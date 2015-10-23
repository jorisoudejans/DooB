package doob.levelBuilder;

import javafx.scene.input.MouseEvent;



/**
 * This class represents the player element, which can be added to a level in the levelbuilder.
 *
 */
public class PlayerElement extends DoobElement {
	
	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 72;
	public static final int PLAYER_Y = 575;
	public static final int MAX_PLAYERS = 2;

	private int width;
	private int height;
	
	/**
	 * Constructor.
	 * @param x X coordinate.
]	 */
	public PlayerElement(double x) {
		super(x, PLAYER_Y);
		width = PLAYER_WIDTH;
		height = PLAYER_HEIGHT;
	}
	
	@Override
	public void handleDrag(MouseEvent event) {
		setX(event.getSceneX() - LevelBuilderController.PANE_X - PlayerElement.PLAYER_WIDTH / 2);
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

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.x && x < this.x + this.width
				&& y >= this.y && y < this.y + this.height);
	}
}
