package doob.controller;

import doob.model.*;
import doob.model.Level.Builder;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Controller for games.
 */
public class GameController {

    @FXML Canvas canvas;

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

/*    public GameController(Canvas canvas) {
        this.canvas = canvas;
        initialize();
    }*/

    /**
     * Initialization of the game pane.
     */
	@FXML
	public void initialize() {
		gameState = GameState.RUNNING;
		level = new Level(canvas);
        level = new LevelFactory("src/main/resources/level/level01.xml", canvas).build();
        createTimer();
        timer.start();
	}

  private GameState gameState;
  private Level level;

  private ArrayList<Ball> balls;
  private ArrayList<Projectile> projectiles;
  private ArrayList<Player> players;
  private int ballSpeed = 3;
  private int shootSpeed = 12;
  private int startHeight = 200;
  private int ballSize = 96;

  private AnimationTimer timer;

  public void createTimer() {
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        level.update();
        updateScore();
        updateProgressBar();
        updateLives();
        if(level.ballPlayerCollision()){
          if(players.get(0).getLives() == 1) {
            level.drawGameOver();
          } else {
            level.drawCrushed();
          }
          createFreeze();
        }
      }
    };
  }

  public void updateScore() {
    int score = level.getScore();
    score1.setText(score + "");
  }

  public void updateProgressBar() {
    double progress = level.getCurrentTime() / level.getTime();
    if (progress <= 0) {
      createFreeze();
      return;
    }
    progressBar.setProgress(progress);
  }

  public void updateLives() {
    gc.clearRect(0, 0, lives1.getWidth(), lives2.getHeight());
    for(Player p : players) {
      for(int i = 0; i < p.getLives(); i++) {
        gc.drawImage(new Image("/image/heart.png"), i * 40, 8);
      }
    }
  }

  public void createFreeze() {
    Task<Void> sleeper = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        return null;
      }
    };
    sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
      public void handle(WorkerStateEvent event) {
        if(players.get(0).getLives() == 1) {
          level.gameOver();
        } else {
          level.crushed();
          timer.start();
        }
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
