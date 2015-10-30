package doob.view.levelbuilder;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;
import doob.model.levelbuilder.CeilingElement;

/**
 * The view of the ceiling element class.
 *
 */
public class CeilingElementView extends BuilderElementView implements Observer {
	
	private CeilingElement ce;
	
	/**
	 * Constructor.
	 * @param ce CeilingElement that it observes.
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
