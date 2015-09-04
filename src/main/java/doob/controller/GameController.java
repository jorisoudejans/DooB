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

    /**
     * Initialization of the game pane.
     */
	@FXML
	public void initialize() {
		projectiles = new ArrayList<Projectile>();
		gameState = GameState.RUNNING;
		level = new Level(canvas);
	}

    /**
     * States the game can be in.
     */
	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
}
