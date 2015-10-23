package doob.levelBuilder;

import java.util.Observable;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Superclass of all drawingelements.
 */
public abstract class DoobElement extends Observable {

	protected double x;
	protected double y;
	protected boolean placed;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 */
	public DoobElement(double x, double y) {
		this.x = x;
		this.y = y;
		placed = false;
	}

	/**
	 * Handle what to do when the mouse drags the object.
	 * 
	 * @param event
	 *            The mouse event that caused the call to this method.
	 */
	public abstract void handleDrag(MouseEvent event);

	/**
	 * Pretend the element has changed to be able to notify the observers.
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}

	public boolean withinBorders(Pane pane) {
		return (x >= 0 && x < pane.getLayoutX() + pane.getWidth() && y >= 0 && y < pane
				.getLayoutY() + pane.getHeight());
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

}
