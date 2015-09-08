package doob.controller;

import doob.model.Ball;
import doob.model.Level;
import doob.model.Player;
import doob.model.Projectile;
import doob.model.Wall;
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

  @FXML
  private Pane lives1;
  @FXML
  private Pane lives2;
  @FXML
  private Label score1;
  @FXML
  private Label score2;
  @FXML
  private ProgressBar progressBar;

  @FXML
  private Canvas canvas;
  private GraphicsContext gc;
  private AnimationTimer timer;

  private GameState gameState;
  private Level level;

  private ArrayList<Ball> balls;
  private ArrayList<Projectile> projectiles;
  private int ballSpeed = 3;
  private int shootSpeed = 12;
  private int startHeight = 200;
  private int ballSize = 96;

  /**
   * Initialization of the game pane.
   */
  @FXML
  public void initialize() {
    gameState = GameState.RUNNING;
    Builder b = new Builder();
    b.setCanvas(canvas);
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(new Player((int) (canvas.getWidth() / 2), (int) (canvas.getHeight() - 72), 50, 72));
    b.setPlayers(players);
    balls = new ArrayList<Ball>();
    balls.add(new Ball(0, startHeight, 3, 0, ballSize));
    b.setBalls(balls);
    level = b.build();
    createTimer();
    timer.start();
  }

  public void createTimer() {
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        level.update();
        updateScore();
        updateProgressBar();
        if(level.ballPlayerCollision()){
          level.drawCrushed();
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
        level.gameOver();
        timer.start();
      }
    });
    new Thread(sleeper).start();
    timer.stop();

  }

  /**
   * States the game can be in.
   */
  public enum GameState {
    PAUSED, RUNNING, WON, LOST
  }
}
