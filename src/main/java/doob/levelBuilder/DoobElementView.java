package doob.levelBuilder;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;

/**
 * Super class for the view objects of the elements in the levelbuilder.
 * Here the elements are being drawn to the canvas.
 */
public class DoobElementView implements Observer {

	private DoobElement observable;
	private GraphicsContext gc;
	
	/**
	 * Constructor.
	 * @param observable The doobelement object that is observed.
	 * @param gc The graphics context object that can draw to the canvas.
	 */
	public DoobElementView(DoobElement observable, GraphicsContext gc) {
		this.observable = observable;
		this.gc = gc;
	}

	@Override
	public void update(Observable o, Object arg) {
		draw(gc);
	}
	
	/**
	 * Draw a view of the element to the canvas.
	 * @param gc The graphics context objects that can draw to the canvas.
	 */
	public void draw(GraphicsContext gc) {
		gc.drawImage(observable.getImage(), observable.getX(), observable.getY());
	}

}
