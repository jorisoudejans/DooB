package doob.model.levelbuilder;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;

/**
 * This class represents the wall element, which can be added to a level in the levelbuilder.
 *
 */
public class WallElement extends DoobElement {
	
	public static final int WALL_WIDTH = 100;
	public static final int WALL_HEIGHT = 650;
	public static final int WALL_Y = 0;
	public static final int MAX_WALLS = 2;
	public static final String IMAGE_PATH = "/image/wall.png";
	
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
	public void image() {	
		image = new Image(IMAGE_PATH);
	}
	
	@Override
	public void drop(DragEvent event) {
		setXCoord(event.getX() - image.getWidth() / 2);
		setYCoord(0);
		change();
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.xCoord && x < this.xCoord + this.width
				&& y >= this.yCoord && y < this.yCoord + this.height);
	}
	
	/**
	 * Return the amount of elements of this class in the given list.
	 * @param elementList The list where to check the amount of elements from.
	 * @return The amount of elements of this class in the list.
	 */
	public static int getAmount(ArrayList<DoobElement> elementList) {
		int res = 0;
		for (DoobElement el : elementList) {
			if (el instanceof WallElement) {
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
}
