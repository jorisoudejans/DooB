package doob.controller;

import java.io.File;
import java.util.ArrayList;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import doob.App;
import doob.model.Level;
import doob.model.LevelFactory;
import doob.model.Player;

/**
 * Controller for games.
 */
public class GameController {

  @FXML
  private Canvas canvas;

  @FXML
  private Canvas lives1;
  @FXML
  private Canvas lives2;
  @FXML
  private Label score1;
  @FXML
  private Label score2;
  @FXML
  private ProgressBar progressBar;
  @FXML
  private Label levelLabel;

  /*
   * public GameController(Canvas canvas) { this.canvas = canvas; initialize(); }
   */

  /**
   * Initialization of the game pane.
   */
  @FXML
  public void initialize() {
    levelList = new ArrayList<String>();
    readLevels();
    levelLabel.setText((currentLevel + 1) + "");
    gameState = GameState.RUNNING;
    level = new Level(canvas);
    level = new LevelFactory(levelList.get(currentLevel), canvas).build();
    gc = lives1.getGraphicsContext2D();
    createTimer();
    timer.start();
  }

  private GameState gameState;
  private ArrayList<String> levelList;
  private int currentLevel;
  private Level level;
  private AnimationTimer timer;
  private GraphicsContext gc;
  public static final long FREEZE_TIME = 2000;

  /**
   * Initializes the timer which uses an animationtimer to handle game steps. The method handle
   * defines what to do in each gamestep.
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
   * Reads all levels out of the levelsfile as a string and puts them in a list of strings with all
   * levels.
   */
  public void readLevels() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = factory.newDocumentBuilder();

      Document doc = dBuilder.parse(new File("src/main/resources/level/Levels.xml"));
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
   * Checks if a player collides with a ball. If so the game freezes and shows a text depending on
   * how many lives the player has left.
   */
  public void checkCollisions() {
    if (level.ballPlayerCollision()) {
      if (level.getPlayers().get(0).getLives() == 1) {
        level.drawText(new Image("/image/gameover.png"));
      } else {
        level.drawText(new Image("/image/crushed.png"));
      }
      createFreeze();
    }
  }

  /**
   * Checks if there are any balls left. If not the game freezes and shows a text.
   */
  public void checkLevelComplete() {
    if (level.getBalls().size() <= 0) {
      level.drawText(new Image("/image/levelcomplete.png"));
      createFreeze();
    }
  }

  /**
   * Updates the score every gamestep.
   */
  public void updateScore() {
    int score = level.getScore();
    score1.setText(score + "");
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

  /**
   * Updates the amount of lives every gamestep.
   */
  public void updateLives() {
    gc.clearRect(0, 0, lives1.getWidth(), lives2.getHeight());
    for (Player p : level.getPlayers()) {
      for (int i = 0; i < p.getLives(); i++) {
        gc.drawImage(new Image("/image/heart.png"), i * 40, 8);
      }
    }
  }

  /**
   * Resets the level depending on currentLevel. Only keeps amount of lives.
   */
  public void newLevel() {
    int lives = level.getPlayers().get(0).getLives();
    level = new LevelFactory(levelList.get(currentLevel), canvas).build();
    level.getPlayers().get(0).setLives(lives);
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

  /**
   * Defines what happens after the 2 seconds of freeze depending on amount of lives and balls.
   */
  public void afterFreeze() {
    if (level.getPlayers().get(0).getLives() == 1) {
      level.gameOver();
    } else if (level.getBalls().size() == 0) {
      if (currentLevel < levelList.size() - 1) {
        currentLevel++;
        levelLabel.setText((currentLevel + 1) + "");
        newLevel();
        timer.start();
      } else {
        App.loadScene("/fxml/menu.fxml");
      }
    } else {
      level.crushed();
      newLevel();
      timer.start();
    }
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
