package doob.levelBuilder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;



/**
 * This class represents the wall element, which can be added to a level in the levelbuilder.
 *
 */
public class WallElement extends DoobElement {
	
	public static final int WALL_WIDTH = 100;
	public static final int WALL_HEIGHT = 650;
	public static final int WALL_Y = 0;
	public static final int MAX_WALLS = 2;
	public static final String IMAGE_PATH = "/image/wall.png";
	
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
		image = new Image(IMAGE_PATH);
	}
	
	@Override
	public void drop(DragEvent event) {
		setX(event.getX() - image.getWidth() / 2);
		setY(0);
		draw();
	}
	
	@Override
	public void draw() {
		gc.drawImage(image, x, y);
	}

	public int getWidth() {
		return width;
	}

	@Override
	public boolean liesInside(double x, double y) {
		return (x >= this.x && x < this.x + this.width
				&& y >= this.y && y < this.y + this.height);
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
