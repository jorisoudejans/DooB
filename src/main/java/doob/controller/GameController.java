package doob.controller;

import doob.App;
import doob.DLog;
import doob.level.LevelFactory;
import doob.level.LevelObserver;
import doob.model.Level;
import doob.model.Player;
import javafx.animation.AnimationTimer;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for games.
 */
public class GameController implements LevelObserver {

	@FXML
	private Canvas canvas;
	@FXML
	private Pane pane;
	@FXML
	private Canvas lives1;
	@FXML
	private Canvas lives2;
	@FXML
	private Label scoreTextView1;
	@FXML
	private Label scoreTextView2;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label levelLabel;

	private GameState gameState;
	private ArrayList<String> levelList;
	private int currentLevel;
	private Level level;
	private AnimationTimer timer;
	private GraphicsContext gc;
	private double progress;
	public static final int HEART_SPACE = 40;
	public static final int HEART_Y = 8;

	/*
	 * public GameController(Canvas canvas) { this.canvas = canvas;
	 * initialize(); }
	 */

	/**
	 * Initialization of the game pane.
	 * 
	 * @throws IOException
	 *             it DooB.log can't be accessed.
	 */
	@FXML
	public void initialize() throws IOException {
		levelList = new ArrayList<String>();
		readLevels();
		levelLabel.setText((currentLevel + 1) + "");
		gameState = GameState.RUNNING;
		createTimer();
		level = new LevelFactory(levelList.get(currentLevel), canvas).build();
		level.addObserver(this);
		gc = lives1.getGraphicsContext2D();
		Canvas background = new Canvas(canvas.getWidth(), canvas.getHeight());
		GraphicsContext gc2 = background.getGraphicsContext2D();
		gc2.drawImage(new Image("/image/background.jpg"), 0, 0, canvas.getWidth(), canvas.getHeight());
		pane.getChildren().add(background);
		background.toBack();

    //Init log.
    DLog.setFile("DooB.log");
    DLog.info("Game started.", DLog.Type.STATE);
  }

	/**
	 * Initializes the timer which uses an animationtimer to handle game steps.
	 * The method handle defines what to do in each gamestep.
	 */
	public void createTimer() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				updateScore();
				updateProgressBar();
				updateLives();
				checkCollisions();
			}
		};
		timer.start();
	}

	/**
	 * Reads all levels out of the levelsfile as a string and puts them in a
	 * list of strings with all levels.
	 */
	public void readLevels() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();

			Document doc = dBuilder.parse(new File(
					"src/main/resources/level/Levels.xml"));
			doc.getDocumentElement().normalize();

			NodeList levels = doc.getElementsByTagName("LevelName");
			for (int i = 0; i < levels.getLength(); i++) {
				Node n = levels.item(i);
				String s = "src/main/resources/level/" + n.getTextContent();
				levelList.add(s);
			}
			currentLevel = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if a player collides with a ball. If so the game freezes and shows
	 * a text depending on how many lives the player has left.
	 */
	public void checkCollisions() {
		if (level.ballPlayerCollision()) {
			if (level.getPlayers().get(0).getState() == Player.State.INVULNERABLE) {
				/*for (final PowerUp powerUp : level.getActivePowerups()) {
					if (powerUp instanceof ProtectOncePowerUp) {
						if (powerUp.getPlayer().equals(
								level.getPlayers().get(0))) {
							final ScheduledExecutorService executorService = Executors
									.newSingleThreadScheduledExecutor();
							executorService.schedule(new Callable<Void>() {
								@Override
								public Void call() throws Exception {
									powerUp.onDeactivate(level);
									return null;
								}
							}, 500, TimeUnit.MILLISECONDS);
						}
					}
				}*/
				return;
			}
			if (level.getPlayers().get(0).getLives() == 1) {
				level.drawText(new Image("/image/gameover.png"));
			} else {
				level.drawText(new Image("/image/crushed.png"));
			}
		}
	}

	/**
	 * Updates the score every gamestep.
	 */
	public void updateScore() {
		int score = level.getPlayers().get(0).getScore();
		scoreTextView1.setText(score + "");
	}

	/**
   * Updates the timebar every gamestep. If there is no time left the game freezes and the level
   * restarts.
   */
  public void updateProgressBar() {
    double progress = level.getCurrentTime() / level.getTime();
    if (progress <= 0) {
		onLevelStateChange(Level.State.NO_TIME_LEFT);
      	return;
    }
    progressBar.setProgress(progress);
  }

	public void emptyProgressBar() {
		progress = level.getCurrentTime() / level.getTime();
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				progress = progress - 0.01;
				if (progress <= 0) {
					this.stop();
				} else {
					progressBar.setProgress(progress);
					level.getPlayers().get(0).incrScore(3);
					updateScore();
				}
			}
		}.start();
	}

	/**
	 * Updates the amount of lives every gamestep.
	 */
	public void updateLives() {
		gc.clearRect(0, 0, lives1.getWidth(), lives2.getHeight());
		for (Player p : level.getPlayers()) {
			for (int i = 0; i < p.getLives(); i++) {
				gc.drawImage(new Image("/image/heart.png"), i * HEART_SPACE,
						HEART_Y);
			}
		}
	}
  
	/**
	 * Resets the level depending on currentLevel. Only keeps amount of lives.
	 */
	public void newLevel() {
		int lives = level.getPlayers().get(0).getLives();
		int score = level.getPlayers().get(0).getScore();
		level = new LevelFactory(levelList.get(currentLevel), canvas).build();
		level.getPlayers().get(0).setLives(lives);
		level.getPlayers().get(0).setScore(score);
	}

	public Level getLevel() {
		return level;
	}

	public GameState getGameState() {
		return gameState;
	}

	@Override
	public void onLevelStateChange(Level.State state) {
		switch (state) {
			case ZERO_LIVES:

				break;
			case ALL_BALLS_GONE:
				onAllBallsGone();
				break;
			case LOST_LIFE:
				break;
			case NO_TIME_LEFT:
				onNoTimeLeft();
				break;
			default:
				break;
		}
		newLevel();
	}

	/**
	 * Handle gameStateChange no time left.
	 */
	public void onNoTimeLeft() {
		newLevel();
	}

	/**
	 * Handle gameStateChange all balls gone.
	 */
	public void onAllBallsGone() {
		emptyProgressBar();
		level.drawText(new Image("/image/levelcomplete.png"));
		DLog.info("Level " + currentLevel + 1 + " completed!", DLog.Type.STATE);
		level.freeze(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				if (currentLevel < levelList.size() - 1) {
					currentLevel++;
					levelLabel.setText((currentLevel + 1) + "");
					newLevel();
					timer.start();
				} else {
					gameState = GameState.WON;
					DLog.info("Game won!", DLog.Type.STATE);
					App.loadScene("/fxml/menu.fxml");
				}
			}
		});
	}

	/**
	 * States the game can be in.
	 */
	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
}
