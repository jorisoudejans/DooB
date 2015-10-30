package doob.model.levelbuilder;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;

/**
 * Superclass of all drawingelements.
 */
public abstract class DoobElement extends Observable {

	protected double xCoord;
	protected double yCoord;
	protected boolean placed;
	protected Image image;

	/**
	 * Constructor.
	 * 
	 * @param xCoord
	 *            X coordinate.
	 * @param yCoord
	 *            Y coordinate.
	 */
	public DoobElement(double xCoord, double yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		placed = false;
	}

	/**
	 * Handle what to do when the element is dropped on the canvas.
	 * 
	 * @param event
	 *            The drag event that caused the call to this method.
	 */
	public abstract void drop(DragEvent event);
		
	/**
	 * Determines if shape lies within border if it was to be dropped at location x,y.
	 * @param x location x
	 * @param y location y
	 * @return whether it lies inside
	 */
	public abstract boolean liesInside(double x, double y);
	
	/**
	 * Pretend the object is changed and notify all observers.
	 */
	public void change() {
		setChanged();
		notifyObservers();
	}
	
	public double getXCoord() {
		return xCoord;
	}

	public double getYCoord() {
		return yCoord;
	}

	public void setXCoord(double x) {
		this.xCoord = x;
	}

	public void setYCoord(double y) {
		this.yCoord = y;
	}

	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	/**
	 * To set the image.
	 */
	public abstract void image();

}
