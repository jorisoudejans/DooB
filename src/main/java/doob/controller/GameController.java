package doob.controller;

import java.awt.event.KeyListener;
import java.util.ArrayList;

import doob.model.Ball;
import doob.model.Player;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GameController{
	
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

	int imageX;
	int imageY;
	private ArrayList<Ball> balls;
	private int ballSpeed = 3;
	private int startHeight = 200;
	private int ballSize = 100;

	private Player player;

	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
	private GameState gameState;

	@FXML
	public void initialize() {
		gameState = GameState.RUNNING;
		imageX = 400;
		imageY = (int) (canvas.getHeight() - 80);
		player = new Player(400, (int) (canvas.getHeight() - 80), 0, 200);
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.RIGHT) && imageX < canvas.getWidth() - 40) {
					imageX += 20;
					player.moveRight();
					System.out.println("Rechts");
				} else if (key.getCode().equals(KeyCode.LEFT) && imageX > 20) {
					imageX -= 20;
					player.moveLeft();
					System.out.println("Links");
				} else if (key.getCode().equals(KeyCode.RIGHT) && imageX > canvas.getWidth() - 40 && imageX < canvas.getWidth() - 20) {
					imageX = (int) (canvas.getWidth() - 20);
				} else if (key.getCode().equals(KeyCode.LEFT) && imageX > 0 && imageX <= 20) {
					imageX = 0;
					System.out.println("Links");
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
	
	public void moveBalls() {
		for(Ball b : balls) {
			b.moveHorizontally();
			if(b.getX() + b.getSize() >= canvas.getWidth()) b.setSpeedX(-ballSpeed);
			else if(b.getX() <= 0) b.setSpeedX(ballSpeed);
			b.moveVertically();;
			b.incrSpeedY(0.5);
			if(b.getY() + b.getSize() > canvas.getHeight()) {
				b.setSpeedY(-20);
			}
		}
	}
	
	public void paint() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		player.draw(gc);
		for(Ball b : balls) {
			b.draw(gc);
		}
	}

	/**
	 * Determines the collisions in the game.
	 */
	public void collide() {
		for (Ball b : balls) {
			if (player.collides(b)) {
				gameState = GameState.LOST;
			}
		}
	}

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
}
