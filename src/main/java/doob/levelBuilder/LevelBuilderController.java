package doob.levelBuilder;

import java.util.ArrayList;
import java.util.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import doob.App;
import doob.levelBuilder.view.BallElementView;
import doob.model.Ball;

/**
 * This class is used to controll the levelbuilder.
 *
 */
public class LevelBuilderController {

	private BallElement be;
	private WallElement we;
	private PlayerElement pe;
	private ArrayList<Observable> elementList;

	@FXML
	private Circle ballButton;
	@FXML
	private ChoiceBox<Integer> ballSizeChoice;
	@FXML
	private CheckBox isMovingDown;
	@FXML
	private CheckBox canOpen;
	@FXML
	private ImageView playerView;
	@FXML
	private Pane pane;
	@FXML
	private Canvas canvas;
	private GraphicsContext gc;

	/**
	 * Initialize the builder.
	 */
	@FXML
	public void initialize() {
		gc = canvas.getGraphicsContext2D();
		elementList = new ArrayList<Observable>();
		initializeOptions();
		initializeEventHandlers();
	}

	/**
	 * Initialize the size options of the ball elements.
	 */
	public void initializeOptions() {
		ballSizeChoice.setItems(FXCollections.observableArrayList(Ball.MEGA,
				Ball.BIG, Ball.MEDIUM, Ball.SMALL));
		ballSizeChoice.getSelectionModel().select(0);
		ballSizeChoice.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observable,
							Number oldValue, Number newValue) {
						ballButton.setRadius(ballSizeChoice.getValue() / 2);
						ballButton.setCenterX(ballButton.getRadius());
						ballButton.setCenterY(ballButton.getRadius());
						ballButton.setFill(Ball.getColor(ballSizeChoice
								.getValue()));
						ballButton.setStroke(Ball.getColor(ballSizeChoice
								.getValue()));
					}
				});
	}

	/**
	 * Initialize all drag and drop functionality of all the elements.
	 */
	public void initializeEventHandlers() {
		initializeBallButton();
	}

	/**
	 * Initialize all drag and drop functionality of the ball element.
	 */
	public void initializeBallButton() {
		setOnDragDetected();
		setOnDragging();
		setOnDragDropped();
	}
	
	/**
	 * Initialize drag detection of the ball element, i.e. create new ball element with observer.
	 */
	public void setOnDragDetected() {
		ballButton.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				be = new BallElement(event.getX(), event.getY(), ballSizeChoice
						.getValue(), Ball.START_SPEED_X, 0);
				be.addObserver(new BallElementView(be, gc));
				elementList.add(be);
				event.consume();
			}
		});
	}
	
	/**
	 * Initialize dragging functionality of the ball element, i.e. draw the ball while dragging it.
	 */
	public void setOnDragging() {
		ballButton.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (be != null && !be.isPlaced()) {
					gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc
							.getCanvas().getHeight());
					be.setX(event.getX());
					be.setY(event.getY());
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
				event.consume();
			}
		});
	}
	
	/**
	 * Initialize dropped detection of the ball element, 
	 * i.e. check if it is within the borders of the game.
	 * If so than place it. If not delete it from the element list.
	 */
	public void setOnDragDropped() {
		ballButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (withinBorders(event.getX(), event.getY())) {
					be.setPlaced(true);
					be = null;
				} else if (be != null) {
					elementList.remove(elementList.size() - 1);
					gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc
							.getCanvas().getHeight());
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
			}
		});
	}

	/**
	 * Checks if parameters x and y are within the borders of the game panel.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @return True when within the borders, false else.
	 */
	private boolean withinBorders(double x, double y) {
		return (x > pane.getLayoutX()
				&& x < pane.getLayoutX() + pane.getWidth()
				&& y > pane.getLayoutY() && y < pane.getLayoutY()
				+ pane.getHeight());
	}

	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}

}
