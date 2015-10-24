package doob.levelBuilder;

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
		draw();
	}
	
	@Override
	public void draw() {
		gc.drawImage(image, x, y);
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.x && x < this.x + this.width
				&& y >= this.y && y < this.y + this.height);
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
