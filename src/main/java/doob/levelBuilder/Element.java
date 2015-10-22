package doob.levelBuilder;

import java.util.Observable;

/**
 * Superclass of all drawingelements.
 */
public abstract class Element extends Observable {

	protected double x;
	protected double y;
	protected boolean placed;
	
	/**
	 * Constructor.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Element(double x, double y) {
		this.x = x;
		this.y = y;
		placed = false;
	}
	
	/**
	 * Pretend the element has changed to be able to notify the observers.
	 */
	public void change() {
		setChanged();
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
