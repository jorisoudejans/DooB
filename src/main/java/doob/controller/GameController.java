package doob.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import doob.model.Ball;
import doob.model.Player;
import doob.model.Player.Direction;
import doob.model.Projectile;
import doob.model.Spike;

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
	private ArrayList<Projectile> projectiles;
	private int ballSpeed = 3;
	private int shootSpeed = 12;
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
		player = new Player(400, (int) (canvas.getHeight() - 80), 40, 200);
		projectiles = new ArrayList<Projectile>();
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.RIGHT) && player.getX() < canvas.getWidth() - 40) {
					player.setSpeed(6);
					player.setDirection(Direction.RIGHT);
					System.out.println("Rechts");
				} else if (key.getCode().equals(KeyCode.LEFT) && player.getX() > 20) {
					player.setSpeed(-6);
					player.setDirection(Direction.LEFT);
					System.out.println("Links");
				} else if (key.getCode().equals(KeyCode.RIGHT) && player.getX() >= canvas.getWidth() - 40) {
					player.setSpeed(0);
				} else if (key.getCode().equals(KeyCode.LEFT) && player.getX() <= 0) {
					player.setSpeed(0);
					System.out.println("Links");
				} else if (key.getCode().equals(KeyCode.SPACE)) {
					projectiles.add(new Spike(player.getX() + player.getWidth() / 2, canvas.getHeight(), shootSpeed));
					System.out.println("Shoot");
				}
			}

		});
		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				player.setSpeed(0);
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
	
	public void shootProjectiles() {
		for (Iterator<Projectile> iter = projectiles.listIterator(); iter.hasNext(); ) {
		    Projectile p = iter.next();
		    if (p.getY() <= 0) {
		        iter.remove();
		    } else {
		    	p.shoot();
		    }
		}

	}
	
	public void paint() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Projectile b : projectiles) {
			gc.drawImage(b.getImg(), b.getX(), b.getY());
		}
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
	
	public void detectCollisions() {
		for(Ball b : balls) {
			if(b.getBounds().intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
				//TODO
				System.out.println("Crushed");
			}
			for(Projectile p : projectiles) {
				if(b.getBounds().intersects(p.getX(), p.getY(), p.getImg().getWidth(), p.getImg().getHeight())) {
					//TODO
					System.out.println("HIT");
				}
			}
		}

	}

	public void startTimer() {
		 new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	               moveBalls();
	               shootProjectiles();
	               player.move();
	               detectCollisions();
	               paint();
	               //collide();
	            }
	        }.start();
	}
}
