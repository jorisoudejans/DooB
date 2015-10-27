package doob.levelBuilder;

import java.util.ArrayList;

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
	
	/**
	 * Constructor.
	 * @param x X coordinate.
	 * @param gc The graphics object that can draw to the canvas.
	 * @param elementList The list of elements on the canvas.
]	 */
	public PlayerElement(double x, GraphicsContext gc, ArrayList<DoobElement> elementList) {
		super(x, PLAYER_Y, gc);
		width = PLAYER_WIDTH;
		height = PLAYER_HEIGHT;
		image = new Image("/image/character" + getAmount(elementList) + "_stand.png");
		System.out.println(getAmount(elementList));
	}
	
	@Override
	public void drop(DragEvent event) {
		setX(event.getX() - image.getWidth() / 2);
		setY(LevelBuilderController.PANE_HEIGHT - PLAYER_HEIGHT - 1);
		change();
	}
	
	/**
	 * Return the amount of elements of this class in the given list.
	 * @param elementList The list where to check the amount of elements from.
	 * @return The amount of elements of this class in the list.
	 */
	public static int getAmount(ArrayList<DoobElement> elementList) {
		int res = 0;
		for (DoobElement el : elementList) {
			if (el instanceof PlayerElement) {
				res++;
			}
		}
		return res;
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
		return (x >= this.xCoord && x < this.xCoord + this.width
				&& y >= this.yCoord && y < this.yCoord + this.height);
	}
}
