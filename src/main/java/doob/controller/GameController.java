package doob.controller;

import doob.model.Ball;
import doob.model.Level;
import doob.model.Projectile;
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

	public GameController(Canvas c) {
		this.canvas = c;
		initialize();
	}

    /**
     * Initialization of the game pane.
     */
	@FXML
	public void initialize() {
		gameState = GameState.RUNNING;
		level = new Level(canvas);
	}

	/**
	 * Returns the current game's state.
	 * @return the game's state
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Returns the current level.
	 * @return the current level.
	 */
	public Level getLevel() {
		return level;
	}

    /**
     * States the game can be in.
     */
	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
}
