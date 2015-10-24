package doob.levelBuilder;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
	private int players;
	private int walls;
	private int balls;
	private String levelName;

	private static final int BUTTON_WIDTH = 100;
	private static final int TEXT_FIELD_WIDTH = 350;
	private static final int FONT_SIZE = 22;
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
		players = 0;
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
	 * Initialize how to handle when an element is dragged again.
	 */
	public void setCanvasHandler() {
		panelCanvas.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
				event.consume();
			}
		});	
		panelCanvas.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				de.drop(event);
				elementList.add(de);
				event.setDropCompleted(true);
				event.consume();
			}
		});	
		panelCanvas.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				de = getElement(event.getX(), event.getY());
				if (de != null) {
					gc.clearRect(0, 0, panelCanvas.getWidth(), panelCanvas.getHeight());
					de.setPlaced(false);
					elementList.remove(de);
					giveContent(panelCanvas, de.getImage());
					for (DoobElement de : elementList) {
						de.draw();
					}
					if (de instanceof PlayerElement) {
						players--;
					} else if (de instanceof WallElement) {
						walls--;
					} else if (de instanceof BallElement) {
						balls--;
					}
				}
			}
		});
	}
	
	/**
	 * Initialize how to handle the bin where elements can be deleted.
	 */
	public void setDiscardHandler() {
		discardView.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
				event.consume();
			}
		});
		discardView.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				discardView.setImage(new Image("/image/binopen.png"));
			}
		});
		discardView.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				discardView.setImage(new Image("/image/binclosed.png"));
			}
		});
		discardView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				elementList.remove(de);
				de = null;
			}
		});
	}
	
	/**
	 * Add the image of the element to the drag event.
	 * @param n The element to be displayed while dragging.
	 * @param image The image representing the element.
	 */
	public void giveContent(Node n, Image image) {
		Dragboard db = n.startDragAndDrop(TransferMode.ANY);
		ClipboardContent c = new ClipboardContent();
		c.putImage(image);
		db.setContent(c);
	}

	/**
	 * Initialize drag detection of the ball element, i.e. create new ball
	 * element with observer.
	 */
	public void setOnBallDragDetected() {
		ballButton.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (balls >= BallElement.MAX_BALLS) {
					return;
				}
				balls++;
				de = new BallElement(0, 0, ballSizeChoice.getValue(), Ball.START_SPEED_X, 0, gc);
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
				if (walls >= WallElement.MAX_WALLS) {
					return;
				}
				walls++;
				de = new WallElement(0, gc);
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
				if (players >= PlayerElement.MAX_PLAYERS) {
					return;
				}
				de = new PlayerElement(0, players, gc);
				players++;
				giveContent(playerView, de.getImage());
				event.consume();
			}
		});
	}

	private boolean withinBorders(double x, double y, DoobElement de) {
		return de.liesInside(x, y);
	}

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
	 * @throws UnsupportedEncodingException when the encoding is not supported
	 * @throws FileNotFoundException if the file is not found.
	 */
	@FXML
	public void backToMenu() throws FileNotFoundException, UnsupportedEncodingException {
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
	 * @throws FileNotFoundException when the file is not found.
	 * @throws UnsupportedEncodingException when the encoding is not supported.
	 */
	@FXML
	public void saveLevel() throws FileNotFoundException, UnsupportedEncodingException {
		final ArrayList<Ball> ballList = new ArrayList<Ball>();
		final ArrayList<Wall> wallList = new ArrayList<Wall>();
		final ArrayList<Player> playerList = new ArrayList<Player>();
		for (DoobElement de : elementList) {
			if (de instanceof BallElement) {
				BallElement be = (BallElement) de;
				ballList.add(new Ball(be.getX(), be.getY(), be.getSpeedX(), be
						.getSpeedY(), be.getSize()));
			} else if (de instanceof PlayerElement) {
				PlayerElement pe = (PlayerElement) de;
				playerList.add(new Player((int) pe.getX(), (int) pe.getY(), pe
						.getWidth(), pe.getHeight(), new Image(
						"/image/character0_stand.png"), new Image(
						"/image/character0_left.gif"), new Image(
						"/image/character0_right.gif")));
			} else if (de instanceof WallElement) {
				WallElement we = (WallElement) de;
				if (canOpen.isSelected()) {
					wallList.add(new Wall((int) we.getX(), (int) we.getY(), we
							.getWidth(), we.getHeight(), (int) we.getX(),
							(int) we.getHeight() - 100, 1000, 3));
				} else {
					wallList.add(new Wall((int) we.getX(), (int) we.getY(), we
							.getWidth(), we.getHeight()));
				}
			} else if (de instanceof CeilingElement) {
				CeilingElement ce = (CeilingElement) de;
				if (isMovingDown.isSelected()) {
					wallList.add(new Wall((int) ce.getX(), (int) ce.getY(), ce
							.getWidth(), ce.getHeight(), (int) ce.getX(),
							(int) pane.getHeight(), 1000, 1));
				} else {
					wallList.add(new Wall((int) ce.getX(), (int) ce.getY(), ce
							.getWidth(), ce.getHeight()));
				}
			}
		}
		final Stage dialog = new Stage();
		dialog.initOwner(App.getStage());
		Label l = new Label("Please enter the name of the level: ");
		l.setFont(new Font(FONT_SIZE));
		final TextField tf = new TextField();
		tf.setMaxWidth(TEXT_FIELD_WIDTH);		
		Button b = new Button("OK");
		b.setPrefWidth(BUTTON_WIDTH);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String name = tf.getText();
				if (name.length() > 0) {
					levelName = name;
					try {
						new LevelWriter(ballList, wallList, playerList, 
								Integer.parseInt(timeField.getText()), levelName).saveToFXML();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dialog.close();
				}
			}
		});
		
		VBox popUpVBox = new VBox(10);
		popUpVBox.setAlignment(Pos.CENTER);
        popUpVBox.getChildren().add(l);
        popUpVBox.getChildren().add(tf);
        popUpVBox.getChildren().add(b);

        Scene dialogScene = new Scene(popUpVBox, 500, 150);
		dialog.setScene(dialogScene);
		dialog.show();
	}
}
