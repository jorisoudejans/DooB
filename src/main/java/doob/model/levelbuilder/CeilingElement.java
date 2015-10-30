package doob.model.levelbuilder;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.shape.Rectangle;




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
	 */
	public CeilingElement(double y) {
		super(CEILING_X, y);
		width = CEILING_WIDTH;
		height = CEILING_HEIGHT;		
	}
	
	@Override
	public void image() {
		image = new Image(IMAGE_PATH);
	}
	
	@Override
	public void drop(DragEvent event) {
		setYCoord(event.getY() - image.getHeight() / 2);
		setXCoord(0);
		change();
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
