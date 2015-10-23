package doob.levelBuilder.view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;
import doob.levelBuilder.WallElement;

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
		gc.fillRect(we.getX(), we.getY(), we.getWidth(), we.getHeight());
	}

}
