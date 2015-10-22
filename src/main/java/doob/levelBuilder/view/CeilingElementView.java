package doob.levelBuilder.view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import doob.levelBuilder.BallElement;
import doob.levelBuilder.CeilingElement;
import doob.model.Ball;

/**
 * The view of the ball element class.
 *
 */
public class CeilingElementView extends BuilderElementView implements Observer {
	
	private CeilingElement ce;
	
	/**
	 * Constructor.
	 * @param ce BallElement that it observes.
	 * @param gc GraphicsContext that can draw to the screen.
	 */
	public CeilingElementView(CeilingElement ce, GraphicsContext gc) {
		super(ce, gc);
		this.ce = ce;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		draw();
	}
	
	@Override
	public void draw() {
		gc.fillRect(ce.getX(), ce.getY(), ce.getWidth(), ce.getHeight());
	}

}
