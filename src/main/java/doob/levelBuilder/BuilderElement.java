package doob.levelBuilder;

/**
 * Class used to contain some data and possibly logic which is the same 
 * for all elements which can be added in the builder. *
 */
public class BuilderElement {
	
	private double x, y;

	/**
	 * Create a builder element.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public BuilderElement(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
