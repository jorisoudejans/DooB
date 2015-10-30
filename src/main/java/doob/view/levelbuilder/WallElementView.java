package doob.view.levelbuilder;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;
import doob.model.levelbuilder.WallElement;

/**
 * The view of the wall element class.
 *
 */
public class WallElementView extends BuilderElementView implements Observer {
	
	private WallElement we;
	
	/**
	 * Constructor.
	 * @param we WallElement that it observes.
	 * @param gc GraphicsContext that can draw to the screen.
	 */
	public WallElementView(WallElement we, GraphicsContext gc) {
		super(we, gc);
		this.we = we;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		draw();
	}
	
	@Override
	public void draw() {
		gc.fillRect(we.getXCoord(), we.getYCoord(), we.getWidth(), we.getHeight());
	}

}
