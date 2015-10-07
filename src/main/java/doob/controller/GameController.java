package doob.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import doob.DLog;
import doob.model.powerup.PowerUp;
import doob.model.powerup.ProtectOncePowerUp;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import doob.model.Level;
import doob.model.LevelFactory;
import doob.model.Player;
import doob.model.Score;

/**
 * Controller for games.
 */
public class GameController {

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
		level = new Level(canvas);
		level = new LevelFactory(levelList.get(currentLevel), canvas).build();
		gc = lives1.getGraphicsContext2D();
		Canvas background = new Canvas(canvas.getWidth(), canvas.getHeight());
		GraphicsContext gc2 = background.getGraphicsContext2D();
		gc2.drawImage(new Image("/image/background.jpg"), 0, 0, canvas.getWidth(), canvas.getHeight());
		pane.getChildren().add(background);
		background.toBack();
		createTimer();
		timer.start();

    //Init log.
    DLog.setFile("DooB.log");
    DLog.info("Game started.", DLog.Type.STATE);
  }

	private GameState gameState;
	private ArrayList<String> levelList;
	private int currentLevel;
	private Level level;
	private AnimationTimer timer;
	private GraphicsContext gc;
	private double progress;
	public static final long FREEZE_TIME = 2000;
	public static final int HEART_SPACE = 40;
	public static final int HEART_Y = 8;

	/**
	 * Initializes the timer which uses an animationtimer to handle game steps.
	 * The method handle defines what to do in each gamestep.
	 */
	public void createTimer() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				level.update();
				updateScore();
				updateProgressBar();
				updateLives();
				checkCollisions();
				checkLevelComplete();
			}
		};
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
				for (final PowerUp powerUp : level.getActivePowerups()) {
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
				}
				return;
			}
			if (level.getPlayers().get(0).getLives() == 1) {
				level.drawText(new Image("/image/gameover.png"));
			} else {
				level.drawText(new Image("/image/crushed.png"));
			}
			createFreeze();
		}
	}

	/**
	 * Checks if there are any balls left. If not the game freezes and shows a
	 * text.
	 */
	public void checkLevelComplete() {
		if (level.getBalls().size() <= 0) {
			level.drawText(new Image("/image/levelcomplete.png"));
			DLog.info("Level " + currentLevel + 1 + " completed!", DLog.Type.STATE);
			emptyProgressBar();
			createFreeze();
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
      createFreeze();
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
   * Defines what happens after the 2 seconds of freeze depending on amount of lives and balls.
   */
  public void afterFreeze() {
    if (level.getPlayers().get(0).getLives() == 1) {
    	DLog.info("Game over!", DLog.Type.STATE);
    	App.loadHighscoreScene(level.getPlayers().get(0).getScore());
    } else if (level.getBalls().size() == 0) {
      if (currentLevel < levelList.size() - 1) {
        currentLevel++;
        levelLabel.setText((currentLevel + 1) + "");
        newLevel();
        timer.start();
      } else {
        //gameState = GameState.WON;
        DLog.info("Game won!", DLog.Type.STATE);
        App.loadHighscoreScene(level.getPlayers().get(0).getScore());
      }
    } else {
      DLog.info("Lost a life", DLog.Type.STATE);
      level.crushed();
      newLevel();
      timer.start();
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

	/**
	 * Create a freeze of 2 seconds when a level fails or is completed.
	 */
	public void createFreeze() {
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(FREEZE_TIME);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent event) {
				afterFreeze();
			}
		});
		new Thread(sleeper).start();
		timer.stop();
	}

	public Level getLevel() {
		return level;
	}

	public GameState getGameState() {
		return gameState;
	}

	/**
	 * States the game can be in.
	 */
	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
}
