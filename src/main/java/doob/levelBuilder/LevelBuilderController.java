package doob.levelBuilder;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import doob.App;
import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;

/**
 * This class is used to controll the levelbuilder.
 *
 */
public class LevelBuilderController {

	private DoobElement de;
	private ArrayList<DoobElement> elementList;

	public static final int LAYOUT = 84;
	public static final int PANE_X = 170;
	public static final int PANE_Y = 20;
	public static final int PANE_HEIGHT = 650;

	@FXML
	private Circle ballButton;
	@FXML
	private Rectangle ceilingButton;
	@FXML
	private Rectangle wallButton;
	@FXML
	private ImageView playerView;
	@FXML
	private ImageView discardView;
	@FXML
	private ChoiceBox<Integer> ballSizeChoice;
	@FXML
	private CheckBox isMovingDown;
	@FXML
	private CheckBox canOpen;
	@FXML
	private Pane pane;
	@FXML
	private Canvas panelCanvas;
	@FXML
	private TextField timeField;
	private GraphicsContext gc;

	/**
	 * Initialize the builder.
	 */
	@FXML
	public void initialize() {
		gc = panelCanvas.getGraphicsContext2D();
		elementList = new ArrayList<DoobElement>();
		initializeOptions();
		initializeImages();
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
	public void initializeImages() {
		playerView.setImage(new Image("/image/character0_stand.png"));
		discardView.setImage(new Image("/image/binclosed.png"));
	}

	/**
	 * Initialize all drag and drop functionality of all the elements.
	 */
	public void initializeEventHandlers() {
		setOnBallDragDetected();
		setOnCeilingDragDetected();
		setOnWallDragDetected();
		setOnPlayerDragDetected();
		setCanvasHandler();
		setDiscardHandler();
	}

	/**
	 * Initialize the drag and drop functionality of the canvas.
	 */
	public void setCanvasHandler() {
		canvasDragOver();
		canvasDragDropped();
		canvasDragDetected();
	}

	/**
	 * Initialize drag and drop functionality of the bin.
	 */
	public void setDiscardHandler() {
		discardDragOver();
		discardDragEntered();
		discardDragExited();
		discardDragDropped();
	}

	/**
	 * Make canvas accept drag gestures.
	 */
	public void canvasDragOver() {
		panelCanvas.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
				event.consume();
			}
		});
	}

	/**
	 * When a doobelement is dropped in the canvas it is placed on the canvas
	 * and added to the elementlist.
	 */
	public void canvasDragDropped() {
		panelCanvas.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				de.drop(event);
				elementList.add(de);
				event.setDropCompleted(true);
				event.consume();
			}
		});
	}

	/**
	 * When there is an element on the location of the drag detection than pick
	 * the element up and remove it from the list.
	 */
	public void canvasDragDetected() {
		panelCanvas.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				de = getElement(event.getX(), event.getY());
				if (de != null) {
					gc.clearRect(0, 0, panelCanvas.getWidth(),
							panelCanvas.getHeight());
					de.setPlaced(false);
					elementList.remove(de);
					giveContent(panelCanvas, de.getImage());
					for (DoobElement de : elementList) {
						de.change();
					}
				}
			}
		});
	}

	/**
	 * Make the discardView (bin) accept drag gestures.
	 */
	public void discardDragOver() {
		discardView.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
				event.consume();
			}
		});
	}

	/**
	 * When a drag gesture enters the discardView (bin) another image is shown.
	 */
	public void discardDragEntered() {
		discardView.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				discardView.setImage(new Image("/image/binopen.png"));
			}
		});
	}

	/**
	 * When a drag gesture exits the discardView (bin) the image is reset.
	 */
	public void discardDragExited() {
		discardView.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				discardView.setImage(new Image("/image/binclosed.png"));
			}
		});
	}

	/**
	 * When an element is dropped in the discardView (bin) it is removed from
	 * the list.
	 */
	public void discardDragDropped() {
		discardView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				elementList.remove(de);
				de = null;
			}
		});
	}

	/**
	 * Initialize drag detection of the ball element, i.e. create new ball
	 * element with observer.
	 */
	public void setOnBallDragDetected() {
		ballButton.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (BallElement.getAmount(elementList) >= BallElement.MAX_BALLS) {
					return;
				}
				de = new BallElement(0, 0, ballSizeChoice.getValue(),
						Ball.START_SPEED_X, 0, gc);
				de.addObserver(new DoobElementView(de, gc));
				giveContent(ballButton, de.getImage());
				event.consume();
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
				de = new CeilingElement(0, gc);
				de.addObserver(new DoobElementView(de, gc));
				giveContent(ceilingButton, de.getImage());
				event.consume();
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
				if (WallElement.getAmount(elementList) >= WallElement.MAX_WALLS) {
					return;
				}
				de = new WallElement(0, gc);
				de.addObserver(new DoobElementView(de, gc));
				giveContent(wallButton, de.getImage());
				event.consume();
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
				if (PlayerElement.getAmount(elementList) >= PlayerElement.MAX_PLAYERS) {
					return;
				}
				de = new PlayerElement(0, gc, elementList);
				de.addObserver(new DoobElementView(de, gc));
				giveContent(playerView, de.getImage());
				event.consume();
			}
		});
	}

	/**
	 * Add the image of the element to the drag event.
	 * 
	 * @param n
	 *            The element to be displayed while dragging.
	 * @param image
	 *            The image representing the element.
	 */
	public void giveContent(Node n, Image image) {
		Dragboard db = n.startDragAndDrop(TransferMode.ANY);
		ClipboardContent c = new ClipboardContent();
		c.putImage(image);
		db.setContent(c);
	}

	/**
	 * Check whether a certain x and y are within the borders of a doob element.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 * @param de
	 *            The DoobElement.
	 * @return True if the coordinates lie inside borders of the element.
	 */
	private boolean withinBorders(double x, double y, DoobElement de) {
		return de.liesInside(x, y);
	}

	/**
	 * Get the DoobElement that is on the give coordinates.
	 * 
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 * @return The DoobElement on the coordinates if any, null else.
	 */
	private DoobElement getElement(double x, double y) {
		for (DoobElement de : elementList) {
			if (withinBorders(x, y, de)) {
				return de;
			}
		}
		return null;
	}

	/**
	 * Navigate back to the menu.
	 * 
	 * @throws UnsupportedEncodingException
	 *             when the encoding is not supported
	 * @throws FileNotFoundException
	 *             if the file is not found.
	 */
	@FXML
	public void backToMenu() throws FileNotFoundException,
			UnsupportedEncodingException {
		App.loadScene("/FXML/Menu.fxml");
	}

	/**
	 * Discard the changes in the levelbuilder.
	 */
	@FXML
	public void discardChanges() {
		App.loadScene("/FXML/levelbuilder.fxml");
	}

	/**
	 * Save the level.
	 * 
	 * @throws FileNotFoundException
	 *             when the file is not found.
	 * @throws UnsupportedEncodingException
	 *             when the encoding is not supported.
	 */
	@FXML
	public void saveLevel() throws FileNotFoundException,
			UnsupportedEncodingException {
		ArrayList<Ball> ballList = parseBalls();
		ArrayList<Wall> wallList = parseWalls();
		ArrayList<Player> playerList = parsePlayers();
		Stage dialog = new Stage();
		LevelBuilderPopupController popup = App.popup(dialog,
				"/FXML/LevelBuilderPopup.fxml").getController();
		popup.initPopup(dialog, timeField, ballList, wallList, playerList);
	}

	/**
	 * Parse all BallElements in the elementlist into a list of balls.
	 * 
	 * @return A list of Ball objects.
	 */
	public ArrayList<Ball> parseBalls() {
		ArrayList<Ball> ballList = new ArrayList<Ball>();
		for (DoobElement de : elementList) {
			if (de instanceof BallElement) {
				BallElement be = (BallElement) de;
				ballList.add(new Ball(be.getX(), be.getY(), be.getSpeedX(), be
						.getSpeedY(), be.getSize()));
			}
		}
		return ballList;
	}

	/**
	 * Parse all WallElements and CeilingElements in the elementlist into a list
	 * of walls.
	 * 
	 * @return A list of Wall objects.
	 */
	public ArrayList<Wall> parseWalls() {
		ArrayList<Wall> wallList = new ArrayList<Wall>();
		for (DoobElement de : elementList) {
			if (de instanceof WallElement) {
				WallElement we = (WallElement) de;
				wallList.add(new Wall((int) we.getX(), (int) we.getY(), we
						.getWidth(), we.getHeight()));
			} else if (de instanceof CeilingElement) {
				CeilingElement ce = (CeilingElement) de;
				if (isMovingDown.isSelected()) {
					wallList.add(new Wall((int) ce.getX(), (int) ce.getY(), ce
							.getWidth(), ce.getHeight(), (int) ce.getX(),
							(int) pane.getHeight(), Integer.MAX_VALUE, 1));
				} else {
					wallList.add(new Wall((int) ce.getX(), (int) ce.getY(), ce
							.getWidth(), ce.getHeight()));
				}
			}
		}
		return wallList;
	}

	/**
	 * Parse all PlayerElements in the elementlist into a list of players.
	 * 
	 * @return A list of Player objects.
	 */
	public ArrayList<Player> parsePlayers() {
		ArrayList<Player> playerList = new ArrayList<Player>();
		for (DoobElement de : elementList) {
			if (de instanceof PlayerElement) {
				PlayerElement pe = (PlayerElement) de;
				playerList.add(new Player((int) pe.getX(), (int) pe.getY(), pe
						.getWidth(), pe.getHeight(), new Image(
						"/image/character0_stand.png"), new Image(
						"/image/character0_left.gif"), new Image(
						"/image/character0_right.gif")));
			}
		}
		return playerList;
	}
}
