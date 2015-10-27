package doob.levelBuilder;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;



/**
 * This class represents the ceiling element, which can be added to a level in the levelbuilder.
 *
 */
public class CeilingElement extends DoobElement {
	
	public static final int CEILING_WIDTH = 960;
	public static final int CEILING_HEIGHT = 100;
	public static final int CEILING_X = 0;
	public static final String IMAGE_PATH = "/image/ceiling.png";
	
	private int width;
	private int height;
		
	/**
	 * Constructor.
	 * @param y Y coordinate.
	 * @param gc The graphics object that can draw to the canvas.
	 */
	public CeilingElement(double y, GraphicsContext gc) {
		super(CEILING_X, y, gc);
		width = CEILING_WIDTH;
		height = CEILING_HEIGHT;
		image = new Image(IMAGE_PATH);
	}
	
	@Override
	public void drop(DragEvent event) {
		setY(event.getY() - image.getHeight() / 2);
		setX(0);
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
	public int getAmount(ArrayList<DoobElement> elementList) {
		int res = 0;
		for (DoobElement el : elementList) {
			if (el instanceof CeilingElement) {
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
