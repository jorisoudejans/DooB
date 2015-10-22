package doob.levelBuilder;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import doob.App;
import doob.levelBuilder.view.BallElementView;
import doob.levelBuilder.view.CeilingElementView;
import doob.levelBuilder.view.PlayerElementView;
import doob.levelBuilder.view.WallElementView;
import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;

/**
 * This class is used to controll the levelbuilder.
 *
 */
public class LevelBuilderController {

	private BallElement be;
	private CeilingElement ce;
	private WallElement we;
	private PlayerElement pe;
	private ArrayList<DoobElement> elementList;
	private String levelName;

	private static final int BUTTON_WIDTH = 100;
	private static final int TEXT_FIELD_WIDTH = 350;
	private static final int FONT_SIZE = 22;
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
	private Canvas panelCanvas;
	private GraphicsContext panelgc;

	/**
	 * Initialize the builder.
	 */
	@FXML
	public void initialize() {
		panelgc = panelCanvas.getGraphicsContext2D();
		elementList = new ArrayList<DoobElement>();
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
		initializeCanvasDrag();
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

	public void initializeCanvasDrag() {
		setOnCanvasDragDetected();
	}

	public void setOnCanvasDragDetected() {
		panelCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				DoobElement de = getElement(event.getX(), event.getY());
				if (de != null) {
					de.setPlaced(false);
					if (de instanceof BallElement) {
						be = (BallElement) de;
						handleOnBallDrag(event);
					} else if (de instanceof WallElement) {
						we = (WallElement) de;
						handleOnWallDrag(event);
					} else if (de instanceof CeilingElement) {
						ce = (CeilingElement) de;
						handleOnCeilingDrag(event);
					} else if (de instanceof PlayerElement) {
						pe = (PlayerElement) de;
						handleOnPlayerDrag(event);
					}
				}
				event.consume();
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
				be = new BallElement(event.getSceneX() - pane.getLayoutX()
						- ballSizeChoice.getValue() / 2, event.getSceneY()
						- pane.getLayoutY() - ballSizeChoice.getValue() / 2,
						ballSizeChoice.getValue(), Ball.START_SPEED_X, 0);
				be.addObserver(new BallElementView(be, panelgc));
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
					handleOnBallDrag(event);
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
					handleOnCeilingDrag(event);
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
					handleOnWallDrag(event);
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
				pe = new PlayerElement(event.getSceneX() - pane.getLayoutX()
						- PlayerElement.PLAYER_WIDTH / 2);
				pe.addObserver(new PlayerElementView(pe, panelgc));
				elementList.add(pe);
				event.consume();
			}
		});
	}

	/**
	 * Initialize dragging functionality of the player element, i.e. draw the
	 * player while dragging it.
	 */
	public void setOnPlayerDragging() {
		playerView.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (pe != null && !pe.isPlaced()) {
					handleOnPlayerDrag(event);
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
				if (withinBorders(event.getSceneX(), pane.getLayoutY())) {
					pe.setPlaced(true);
					pe = null;
				} else if (pe != null) {
					elementList.remove(pe);
					panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
							panelgc.getCanvas().getHeight());
					for (Observable ov : elementList) {
						((DoobElement) ov).update();
					}
				}
			}
		});
	}
	
	public void handleOnBallDrag(MouseEvent event) {
		panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
				panelgc.getCanvas().getHeight());
		be.setX(event.getSceneX() - pane.getLayoutX()
				- be.getSize() / 2);
		be.setY(event.getSceneY() - pane.getLayoutY()
				- be.getSize() / 2);
		for (Observable ov : elementList) {
			((DoobElement) ov).update();
		}
	}
	
	public void handleOnWallDrag(MouseEvent event) {
		panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
				panelgc.getCanvas().getHeight());
		we.setX(event.getSceneX() - pane.getLayoutX()
				- WallElement.WALL_WIDTH / 2);
		for (Observable ov : elementList) {
			((DoobElement) ov).update();
		}
	}
	
	public void handleOnCeilingDrag(MouseEvent event) {
		panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
				panelgc.getCanvas().getHeight());
		ce.setY(event.getSceneY() - pane.getLayoutY()
				- CeilingElement.CEILING_HEIGHT / 2);
		for (Observable ov : elementList) {
			((DoobElement) ov).update();
		}
	}
	
	public void handleOnPlayerDrag(MouseEvent event) {
		panelgc.clearRect(0, 0, panelgc.getCanvas().getWidth(),
				panelgc.getCanvas().getHeight());
		pe.setX(event.getSceneX() - pane.getLayoutX()
				- PlayerElement.PLAYER_WIDTH / 2);
		for (Observable ov : elementList) {
			((DoobElement) ov).update();
		}
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

	private boolean withinBorders(double x, double y, DoobElement de) {
		if (de instanceof BallElement) {
			BallElement be = (BallElement) de;
			return (x >= be.getX() && x < be.getX() + be.getSize()
					&& y >= be.getY() && y < be.getY() + be.getSize());
		} else if (de instanceof WallElement) {
			WallElement we = (WallElement) de;
			return (x >= we.getX() && x < we.getX() + we.getWidth()
					&& y >= we.getY() && y < we.getY() + we.getHeight());
		} else if (de instanceof CeilingElement) {
			CeilingElement ce = (CeilingElement) de;
			return (x >= ce.getX() && x < ce.getX() + ce.getWidth()
					&& y >= ce.getY() && y < ce.getY() + ce.getHeight());
		} else if (de instanceof PlayerElement) {
			PlayerElement pe = (PlayerElement) de;
			return (x >= pe.getX() && x < pe.getX() + pe.getWidth()
					&& y >= pe.getY() && y < pe.getY() + pe.getHeight());
		}
		return false;
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
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@FXML
	public void backToMenu() throws FileNotFoundException, UnsupportedEncodingException {
		App.loadScene("/FXML/Menu.fxml");
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
						new LevelWriter(ballList, wallList, playerList, 2000, 
								levelName).saveToFXML();
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
