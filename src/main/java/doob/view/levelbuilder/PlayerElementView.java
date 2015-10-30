package doob.view.levelbuilder;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import doob.model.levelbuilder.PlayerElement;

/**
 * The view of the ball element class.
 *
 */
public class PlayerElementView extends BuilderElementView implements Observer {
	
	private PlayerElement pe;
	
	/**
	 * Constructor.
	 * @param pe PlayerElement that it observes.
	 * @param gc GraphicsContext that can draw to the screen.
	 */
	public PlayerElementView(PlayerElement pe, GraphicsContext gc) {
		super(pe, gc);
		this.pe = pe;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		draw();
	}
	
	@Override
	public void draw() {
		gc.drawImage(new Image("/image/character0_stand.png"), pe.getXCoord(), pe.getYCoord());
	}

}
