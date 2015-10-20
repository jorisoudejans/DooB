package doob.game;


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
import doob.controller.OptionsController;
import doob.level.LevelObserver;
import doob.model.Level;
import doob.model.Player;


/**
 * Controller for games.
 */
public abstract class Game implements LevelObserver {

	@FXML
	protected Canvas canvas;
	@FXML
	protected Pane pane;
	@FXML
	protected Canvas lives1;
	@FXML
	protected Canvas lives2;
	@FXML
	protected Label scoreTextView1;
	@FXML
	protected Label scoreTextView2;
	@FXML
	protected ProgressBar progressBar;
	@FXML
	protected Label levelLabel;
	@FXML
	protected Button playPauseButton;

	// private GameState gameState;
	protected ArrayList<String> levelList;
	protected int currentLevel;
	protected Level level;
	private AnimationTimer timer;
	protected GraphicsContext gc;
	protected GraphicsContext gc2;
	private double progress;
	private boolean running;
	protected int score;
	protected int score2;
	public static final int HEART_SPACE = 40;
	public static final int HEART_Y = 8;
	private static final int TIME_BONUS = 3;
	private static final double PROGRESS_PER_TICK = 0.01;

	private DLog dLog;

	/**
	 * Initialization of the game pane.
	 * 
	 * @throws IOException
	 *             it DooB.log can't be accessed.
	 * @param levelPath
	 *            The path of the levels to be read.
	 */

	public void initGame(String levelPath) {
		dLog = DLog.getInstance();
		levelList = new ArrayList<String>();
		readLevels(levelPath);
		levelLabel.setText((currentLevel + 1) + "");
		// gameState = GameState.RUNNING;
		createTimer();
		newLevel();
		readOptions();
		gc = lives1.getGraphicsContext2D();
		gc2 = lives2.getGraphicsContext2D();
		Canvas background = new Canvas(canvas.getWidth(), canvas.getHeight());
		GraphicsContext gcBg = background.getGraphicsContext2D();
		gcBg.drawImage(new Image("/image/background.jpg"), 0, 0,
				canvas.getWidth(), canvas.getHeight());
		pane.getChildren().add(background);
		background.toBack();
		running = true;

		dLog.setFile("DooB.log");
		dLog.info("Game started.", DLog.Type.STATE);
	}	

	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		level.stopTimer();
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
	 * 
	 * @param levelPath
	 *            The path to the levels that have to be read.
	 */
	public void readLevels(String levelPath) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();

			Document doc = dBuilder.parse(new File(levelPath));
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
	 * Reads all options from the options xml.
	 */
	public void readOptions() {
		for (int i = 0; i < level.getPlayers().size(); i++) {
			OptionsController oc = new OptionsController(
					"src/main/resources/Options/OptionsPlayer" + (i + 1)
							+ ".xml");
			oc.read();
			Player.ControlKeys keys = new Player.ControlKeys(oc.getLeft(), oc.getRight(), oc.getShoot());
			level.getPlayers().get(i).setControlKeys(keys);
		}
	}

	/**
	 * Updates the timebar every gamestep. If there is no time left the game
	 * freezes and the level restarts.
	 */
	public void updateProgressBar() {
		double progress = level.getCurrentTime() / level.getTime();
		if (progress <= 0) {
			for (Player player : level.getPlayers()) {
				if (player.isAlive()) {
					player.die();
				}
			}
			onLevelStateChange(Level.Event.LOST_LIFE);
			return;
		}
		progressBar.setProgress(progress);
	}

	/**
	 * Empty the progressbar after a level is completed and add points for the remaining time.
	 */
	public void emptyProgressBar() {
		progress = level.getCurrentTime() / level.getTime();
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				progress = progress - PROGRESS_PER_TICK;
				if (progress <= 0) {
					this.stop();
				} else {
					progressBar.setProgress(progress);
					for (Player p : level.getPlayers()) {
						p.incrScore(TIME_BONUS);
					}
					updateScore();
				}
			}
		}.start();
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public void onLevelStateChange(Level.Event event) {
		switch (event) {
		case ZERO_LIVES:
			loadHighscores();
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
		dLog.info("Level " + (currentLevel + 1) + " completed!",
				DLog.Type.STATE);
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
					// gameState = GameState.WON;
					dLog.info("Game won!", DLog.Type.STATE);
					loadHighscores();
				}
			}
		});
	}
	
	/**
	 * Updates the amount of lives every gamestep.
	 */
	public abstract void updateLives();
	
	/**
	 * Updates the score every gamestep.
	 */
	public abstract void updateScore();
	
	/**
	 * Resets the level depending on currentLevel. Only keeps amount of lives.
	 */
	public abstract void newLevel();
	
	/**
	 * Show the highscores.
	 */
	public abstract void loadHighscores();
}
