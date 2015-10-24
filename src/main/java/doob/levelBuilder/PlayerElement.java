package doob.levelBuilder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;



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
	private int player;
	
	/**
	 * Constructor.
	 * @param x X coordinate.
	 * @param player Indicates whether this is player 1 or 2.
	 * @param gc The graphics object that can draw to the canvas.
]	 */
	public PlayerElement(double x, int player, GraphicsContext gc) {
		super(x, PLAYER_Y, gc);
		this.player = player;
		width = PLAYER_WIDTH;
		height = PLAYER_HEIGHT;
		image = new Image("/image/character" + player + "_stand.png");
	}
	
	@Override
	public void drop(DragEvent event) {
		setX(event.getX() - image.getWidth() / 2);
		setY(LevelBuilderController.PANE_HEIGHT - PLAYER_HEIGHT - 1);
		draw();
	}
	
	@Override
	public void draw() {
		gc.drawImage(image, x, y);
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

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.x && x < this.x + this.width
				&& y >= this.y && y < this.y + this.height);
	}
}
