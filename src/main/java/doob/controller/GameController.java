package doob.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import doob.App;
import doob.DLog;
import doob.level.LevelFactory;
import doob.level.LevelObserver;
import doob.model.Level;
import doob.model.Player;

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
	@FXML
	private Button playPauseButton;

	private GameState gameState;
	private ArrayList<String> levelList;
	private int currentLevel;
	private Level level;
	private AnimationTimer timer;
	private GraphicsContext gc;
	private double progress;
	private boolean running;
	public static final int HEART_SPACE = 40;
	public static final int HEART_Y = 8;

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
		newLevel();
		gc = lives1.getGraphicsContext2D();
		Canvas background = new Canvas(canvas.getWidth(), canvas.getHeight());
		GraphicsContext gc2 = background.getGraphicsContext2D();
		gc2.drawImage(new Image("/image/background.jpg"), 0, 0, canvas.getWidth(), canvas.getHeight());
		pane.getChildren().add(background);
		background.toBack();
		running = true;

    DLog.setFile("DooB.log");
    DLog.info("Game started.", DLog.Type.STATE);
  }
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}
	
	/**
	 * Continues or pauses the game dependent on wheter it is running or not.
	 */
	@FXML
	public void pausePlay() {
		if (running) {
			level.stopTimer();
			running = false;
			playPauseButton.setText("Play");
		} else {
			level.startTimer();
			running = true;
			playPauseButton.setText("Pause");
		}
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
		for (Player player : level.getPlayers()) {
			player.die();
		}
		onLevelStateChange(Level.Event.LOST_LIFE);
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
		Player currentPlayer = null;
		if (level != null) {
			currentPlayer = level.getPlayers().get(0);
			level.stopTimer();
		}

		level = new LevelFactory(levelList.get(currentLevel), canvas).build();
		level.addObserver(this);
		if (currentPlayer != null) {
			int lives = currentPlayer.getLives();
			int score = currentPlayer.getScore();
			level.getPlayers().get(0).setLives(lives);
			level.getPlayers().get(0).setScore(score);
		}
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public void onLevelStateChange(Level.Event event) {
		switch (event) {
			case ZERO_LIVES:
				App.loadHighscoreScene(level.getPlayers().get(0).getScore());
				break;
			case ALL_BALLS_GONE:
				onAllBallsGone();
				break;
			case LOST_LIFE:
				newLevel();
				break;
			default:
				break;
		}
	}

	/**
	 * Handle gameStateChange all balls gone.
	 */
	public void onAllBallsGone() {
		emptyProgressBar();
		DLog.info("Level " + (currentLevel + 1) + " completed!", DLog.Type.STATE);
		timer.stop();
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
					App.loadHighscoreScene(level.getPlayers().get(0).getScore());
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
