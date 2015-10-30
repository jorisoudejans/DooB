package doob.levelBuilder;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.DragEvent;
import javafx.scene.shape.Rectangle;



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
	 * @param gc The graphics object that can draw to the canvas.
	 */
	public WallElement(double x, GraphicsContext gc) {
		super(x, WALL_Y, gc);
		width = WALL_WIDTH;
		height = WALL_HEIGHT;
	}
	
	@Override
	public void drop(DragEvent event) {
		setX(event.getX() - image.getWidth() / 2);
		setY(0);
		change();
	}

	public int getWidth() {
		return width;
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.xCoord && x < this.xCoord + this.width
				&& y >= this.yCoord && y < this.yCoord + this.height);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(xCoord, yCoord, width, height);
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
