package doob.levelBuilder;

import java.util.Observable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;

/**
 * Superclass of all drawingelements.
 */
public abstract class DoobElement extends Observable {

	protected double xCoord;
	protected double yCoord;
	protected boolean placed;
	protected GraphicsContext gc;
	protected Image image;

	/**
	 * Constructor.
	 * 
	 * @param xCoord
	 *            X coordinate.
	 * @param yCoord
	 *            Y coordinate.
	 * @param gc The graphics object that can draw to the canvas.
	 */
	public DoobElement(double xCoord, double yCoord, GraphicsContext gc) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.gc = gc;
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
	
	public double getX() {
		return xCoord;
	}

	public double getY() {
		return yCoord;
	}

	public void setX(double x) {
		this.xCoord = x;
	}

	public void setY(double y) {
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

}
