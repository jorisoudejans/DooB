package doob.levelBuilder;

import java.util.Observable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;

/**
 * Superclass of all drawingelements.
 */
public abstract class DoobElement extends Observable {

	protected double x;
	protected double y;
	protected boolean placed;
	protected GraphicsContext gc;
	protected Image image;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 * @param gc The graphics object that can draw to the canvas.
	 */
	public DoobElement(double x, double y, GraphicsContext gc) {
		this.x = x;
		this.y = y;
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
	 * Draws the element to the canvas.
	 */
	public abstract void draw();
	
	/**
	 * Determines if shape lies within border if it was to be dropped at location x,y.
	 * @param x location x
	 * @param y location y
	 * @return whether it lies inside
	 */
	public abstract boolean liesInside(double x, double y);

	/**
	 * Pretend the element has changed to be able to notify the observers.
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
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
