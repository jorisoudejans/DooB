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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import doob.App;
import doob.levelBuilder.view.BallElementView;
import doob.levelBuilder.view.CeilingElementView;
import doob.levelBuilder.view.PlayerElementView;
import doob.levelBuilder.view.WallElementView;
import doob.model.Ball;

/**
 * This class is used to controll the levelbuilder.
 *
 */
public class LevelBuilderController {

	private BallElement be;
	private CeilingElement ce;
	private WallElement we;
	private PlayerElement pe;
	private ArrayList<Observable> elementList;

	public static final int LAYOUT = 84;

	@FXML
	private Circle ballButton;
	@FXML
	private Rectangle ceilingButton;
	@FXML
	private Rectangle wallButton;
	@FXML
	private ImageView playerView;
	@FXML
	private ChoiceBox<Integer> ballSizeChoice;
	@FXML
	private CheckBox isMovingDown;
	@FXML
	private CheckBox canOpen;
	@FXML
	private Pane pane;
	@FXML
	private Canvas canvas;
	@FXML
	private Canvas panelCanvas;
	private GraphicsContext gc;
	private GraphicsContext panelgc;

	/**
	 * Initialize the builder.
	 */
	@FXML
	public void initialize() {
		gc = canvas.getGraphicsContext2D();
		panelgc = panelCanvas.getGraphicsContext2D();
		elementList = new ArrayList<Observable>();
		initializeOptions();
		initializePlayerView();
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
						int value = ballSizeChoice.getValue();
						int radius = value / 2;
						ballButton.setRadius(radius);
						ballButton.setCenterX(radius);
						ballButton.setCenterY(radius);
						ballButton.setLayoutX(LAYOUT - ballButton.getRadius());
						ballButton.setLayoutY(LAYOUT - ballButton.getRadius());
						ballButton.setFill(Ball.getColor(value));
						ballButton.setStroke(Ball.getColor(value));
					}
				});
	}

	/**
	 * The imageview displays the image of a player.
	 */
	public void initializePlayerView() {
		playerView.setImage(new Image("/image/character0_stand.png"));
	}

	/**
	 * Initialize all drag and drop functionality of all the elements.
	 */
	public void initializeEventHandlers() {
		initializeBallButton();
		initializeCeilingButton();
		initializeWallButton();
		initializePlayerButton();
	}

	/**
	 * Initialize all drag and drop functionality of the ball element.
	 */
	public void initializeBallButton() {
		setOnBallDragDetected();
		setOnBallDragging();
		setOnBallDragDropped();
	}

	/**
	 * Initialize all drag and drop functionality of the ceiling element.
	 */
	public void initializeCeilingButton() {
		setOnCeilingDragDetected();
		setOnCeilingDragging();
		setOnCeilingDragDropped();
	}

	/**
	 * Initialize all drag and drop functionality of the wall element.
	 */
	public void initializeWallButton() {
		setOnWallDragDetected();
		setOnWallDragging();
		setOnWallDragDropped();
	}

	/**
	 * Initialize all drag and drop functionality of the player element.
	 */
	public void initializePlayerButton() {
		setOnPlayerDragDetected();
		setOnPlayerDragging();
		setOnPlayerDragDropped();
	}

	/**
	 * Initialize drag detection of the ball element, i.e. create new ball
	 * element with observer.
	 */
	public void setOnBallDragDetected() {
		ballButton.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				be = new BallElement(event.getSceneX()
						- ballSizeChoice.getValue() / 2, event.getSceneY()
						- ballSizeChoice.getValue() / 2, ballSizeChoice
						.getValue(), Ball.START_SPEED_X, 0);
				be.addObserver(new BallElementView(be, gc));
				elementList.add(be);
				event.consume();
			}
		});
	}

	/**
	 * Initialize dragging functionality of the ball element, i.e. draw the ball
	 * while dragging it.
	 */
	public void setOnBallDragging() {
		ballButton.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (be != null && !be.isPlaced()) {
					gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc
							.getCanvas().getHeight());
					be.setX(event.getSceneX() - be.getSize() / 2);
					be.setY(event.getSceneY() - be.getSize() / 2);
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
				event.consume();
			}
		});
	}

	/**
	 * Initialize dropped detection of the ball element, i.e. check if it is
	 * within the borders of the game. If so than place it. If not delete it
	 * from the element list.
	 */
	public void setOnBallDragDropped() {
		ballButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (withinBorders(event.getSceneX(), event.getSceneY())) {
					be.setPlaced(true);
					be = null;
				} else if (be != null) {
					elementList.remove(be);
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
	 * Initialize drag detection of the ceiling element, i.e. create new ceiling
	 * element with observer.
	 */
	public void setOnCeilingDragDetected() {
		ceilingButton.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ce = new CeilingElement(event.getSceneY() - pane.getLayoutY()
						- CeilingElement.CEILING_HEIGHT / 2);
				ce.addObserver(new CeilingElementView(ce, panelgc));
				elementList.add(ce);
				event.consume();
			}
		});
	}

	/**
	 * Initialize dragging functionality of the ceiling element, i.e. draw the
	 * ceiling while dragging it.
	 */
	public void setOnCeilingDragging() {
		ceilingButton.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (ce != null && !ce.isPlaced()) {
					panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
							panelgc.getCanvas().getHeight());
					ce.setY(event.getSceneY() - pane.getLayoutY()
							- CeilingElement.CEILING_HEIGHT / 2);
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
				event.consume();
			}
		});
	}

	/**
	 * Initialize dropped detection of the ceiling element, i.e. check if it is
	 * within the borders of the game. If so than place it. If not delete it
	 * from the element list.
	 */
	public void setOnCeilingDragDropped() {
		ceilingButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (withinBorders(pane.getLayoutX(), event.getSceneY())) {
					ce.setPlaced(true);
					ce = null;
				} else if (ce != null) {
					elementList.remove(ce);
					panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
							panelgc.getCanvas().getHeight());
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
			}
		});
	}

	/**
	 * Initialize drag detection of the wall element, i.e. create new wall
	 * element with observer.
	 */
	public void setOnWallDragDetected() {
		wallButton.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				we = new WallElement(event.getSceneX() - pane.getLayoutX()
						- WallElement.WALL_WIDTH / 2);
				we.addObserver(new WallElementView(we, panelgc));
				elementList.add(we);
				event.consume();
			}
		});
	}

	/**
	 * Initialize dragging functionality of the wall element, i.e. draw the wall
	 * while dragging it.
	 */
	public void setOnWallDragging() {
		wallButton.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (we != null && !we.isPlaced()) {
					panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
							panelgc.getCanvas().getHeight());
					we.setX(event.getSceneX() - pane.getLayoutX()
							- WallElement.WALL_WIDTH / 2);
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
				event.consume();
			}
		});
	}

	/**
	 * Initialize dropped detection of the wall element, i.e. check if it is
	 * within the borders of the game. If so than place it. If not delete it
	 * from the element list.
	 */
	public void setOnWallDragDropped() {
		wallButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (withinBorders(event.getSceneX(), pane.getLayoutX())) {
					we.setPlaced(true);
					we = null;
				} else if (we != null) {
					elementList.remove(we);
					panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
							panelgc.getCanvas().getHeight());
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
			}
		});
	}

	/**
	 * Initialize drag detection of the player element, i.e. create new player
	 * element with observer.
	 */
	public void setOnPlayerDragDetected() {
		playerView.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				pe = new PlayerElement(event.getSceneX()
						- PlayerElement.PLAYER_WIDTH / 2, event.getSceneY()
						- PlayerElement.PLAYER_HEIGHT / 2);
				pe.addObserver(new PlayerElementView(pe, gc));
				elementList.add(pe);
				event.consume();
			}
		});
	}

	/**
	 * Initialize dragging functionality of the player element, i.e. draw the player
	 * while dragging it.
	 */
	public void setOnPlayerDragging() {
		playerView.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (pe != null && !pe.isPlaced()) {
					gc.clearRect(0, 0, gc.getCanvas().getWidth(),
							gc.getCanvas().getHeight());
					pe.setX(event.getSceneX() - PlayerElement.PLAYER_WIDTH / 2);
					pe.setY(event.getSceneY() - PlayerElement.PLAYER_HEIGHT / 2);
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
				event.consume();
			}
		});
	}

	/**
	 * Initialize dropped detection of the player element, i.e. check if it is
	 * within the borders of the game. If so than place it. If not delete it
	 * from the element list.
	 */
	public void setOnPlayerDragDropped() {
		playerView.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (withinBorders(event.getSceneX(), event.getSceneY())) {
					pe.setPlaced(true);
					pe = null;
				} else if (pe != null) {
					elementList.remove(pe);
					gc.clearRect(0, 0, gc.getCanvas().getWidth(),
							gc.getCanvas().getHeight());
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
			}
		});
	}

	/**
	 * Checks if parameters x and y are within the borders of the game panel.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 * @return True when within the borders, false else.
	 */
	private boolean withinBorders(double x, double y) {
		return (x >= pane.getLayoutX()
				&& x < pane.getLayoutX() + pane.getWidth()
				&& y >= pane.getLayoutY() && y < pane.getLayoutY()
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
