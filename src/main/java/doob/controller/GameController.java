package doob.controller;

import doob.model.Ball;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
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

	private ArrayList<Ball> balls;
	private int ballSpeed = 3;
	private int startHeight = 200;
	private int ballSize = 100;

	private PlayerController player;

	private GameState gameState;

    /**
     * Initialization of the game pane.
     */
	@FXML
	public void initialize() {
		gameState = GameState.RUNNING;
        player = new PlayerController(canvas);
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case RIGHT:
                        player.moveRight();
                        break;
                    case LEFT:
                        player.moveLeft();
                        break;
                    default:
                        break;
                }
			}

		});
		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				player.stand();
			}

		});
		canvas.requestFocus();
		balls = new ArrayList<Ball>();
		balls.add(new Ball(0, startHeight, ballSpeed, 0, ballSize));
		gc = canvas.getGraphicsContext2D();
		startTimer();
	}

    /**
     * Move the balls.
     */
	public void moveBalls() {
		for (Ball b: balls) {
			b.moveHorizontally();
			if (b.getX() + b.getSize() >= canvas.getWidth()) {
                b.setSpeedX(-ballSpeed);
            } else if (b.getX() <= 0) {
                b.setSpeedX(ballSpeed);
            }
			b.moveVertically();
			b.incrSpeedY(0.5);
			if (b.getY() + b.getSize() > canvas.getHeight()) {
				b.setSpeedY(-20);
			}
		}
	}

    /**
     * Paint all views.
     */
	public void paint() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		player.invalidate(gc);
		for (Ball b: balls) {
			b.draw(gc);
		}
	}

	/**
	 * Loops through every object in the game to detect collisions.
	 */
	public void collide() {
		for (Ball b : balls) {
			if (player.collides(b)) {
				gameState = GameState.LOST;
			}
		}
	}

    /**
     * Timer for animation.
     */
	public void startTimer() {
		 new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	               moveBalls();
	               paint();
					collide();
	            }
	        }.start();
	}

    /**
     * States the game can be in.
     */
	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
}
