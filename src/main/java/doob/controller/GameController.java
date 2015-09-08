package doob.controller;

import doob.model.Ball;
import doob.model.Level;
import doob.model.Player;
import doob.model.Projectile;
import doob.model.Wall;
import doob.model.Level.Builder;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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
	private Canvas canvas;
	private GraphicsContext gc;

	private GameState gameState;
	private Level level;
	
	private ArrayList<Ball> balls;
	private ArrayList<Projectile> projectiles;
	private int ballSpeed = 3;
	private int shootSpeed = 12;
	private int startHeight = 200;
	private int ballSize = 100;

    /**
     * Initialization of the game pane.
     */
	@FXML
	public void initialize() {
		gameState = GameState.RUNNING;
		Builder b = new Builder();
		b.setCanvas(canvas);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(
				(int) (canvas.getWidth() / 2),
				(int) (canvas.getHeight() - 72),
				72,
				50
		));
		b.setPlayers(players);
		balls = new ArrayList<Ball>();
		balls.add(new Ball(
						0,
						startHeight,
						3,
						0,
						ballSize
						));
		b.setBalls(balls);
		level = b.build();	
		startTimer();
	}
	
	public void startTimer() {
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				level.update();
				updateScore();
			}
		}.start();
	}
	
	public void updateScore() {
	  int score = level.getScore();
	  score1.setText(score + "");
	}
    /**
     * States the game can be in.
     */
	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
}
