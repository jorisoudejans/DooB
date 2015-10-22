package doob.levelBuilder;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import doob.App;
import doob.levelBuilder.view.BallElementView;

/**
 * This class is used to controll the levelbuilder.
 *
 */
public class LevelBuilderController {
	
	private BallElement be = new BallElement();
	private WallElement we;
	private PlayerElement pe;
	private BallElementView bev = new BallElementView();
	
	@FXML
	private Canvas canvas;
	private GraphicsContext gc;
	
	/**
	 * Initialize the builder.
	 */
	public void initBuilder() {
		be.addObserver(bev);
		drags();
		gc = canvas.getGraphicsContext2D();
	}
	
	public void drags() {
		
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}

}
