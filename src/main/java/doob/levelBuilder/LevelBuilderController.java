package doob.levelBuilder;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;
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
	private Shape ballButton;
	@FXML
	private ChoiceBox<Integer> ballSizeChoice;
	@FXML
	private CheckBox isMovingDown;
	@FXML
	private CheckBox canOpen;
	@FXML
	private ImageView playerView;
	@FXML
	private Canvas canvas;
	private GraphicsContext gc;
	
	/**
	 * Initialize the builder.
	 */
	@FXML
	public void initialize() {
		be.addObserver(bev);
		gc = canvas.getGraphicsContext2D();
	}
	
	@FXML
	public void pickupBall() {
		
	}
	
	@FXML
	public void dropBall() {
		
	}
	
	@FXML
	public void pickupCeiling() {
		
	}
	
	@FXML
	public void dropCeiling() {
		
	}
	
	@FXML
	public void pickupWall() {
		
	}
	
	@FXML
	public void dropWall() {
		
	}
	
	@FXML
	public void pickupPlayer() {
		
	}
	
	@FXML
	public void dropPlayer() {
		
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}

}
