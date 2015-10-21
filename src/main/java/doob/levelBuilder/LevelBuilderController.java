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
	
	private BallElement be;
	private WallElement we;
	private PlayerElement pe;
	private BallElementView bev;
	
	@FXML
	private Canvas canvas;
	private GraphicsContext gc;
	
	@FXML
	final Text source = new Text(50, 100, "DRAG ME");
	@FXML
	final Text target = new Text(300, 100, "DROP HERE");
	
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
