package doob.model;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Level {

	private Canvas canvas;
	private GraphicsContext gc;

	private ArrayList<Ball> balls;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Player> players;
	private int ballSpeed = 3;
	private int shootSpeed = 12;
	private int startHeight = 200;
	private int ballSize = 100;

	/**
	 * Initialize javaFx.
	 */
	public Level(Canvas canvas) {
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(new KeyPressHandler());

		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				players.get(0).setSpeed(0);
			}
		});
		canvas.requestFocus();
		balls = new ArrayList<Ball>();
		balls.add(new Ball(0, startHeight, ballSpeed, 0, ballSize));
		players = new ArrayList<Player>();
		players.add(new Player(
				(int) (canvas.getWidth() / 2),
				(int) (canvas.getHeight() - 72),
				72,
				50
		));
		startTimer();
		projectiles = new ArrayList<Projectile>();
	}

	/**
	 * Create a new projectile and shoot it.
	 * @param player the player that shoots the projectile.
	 */
	public void shoot(Player player) {
		projectiles.add(new Spike(player.getX(), canvas.getHeight(), shootSpeed));
	}

	/**
	 * Loops through every object in the game to detect collisions.
	 */
	public void detectCollisions() {
		for (Ball b : balls) {
			if (b.getBounds().intersects(players.get(0).getX(), players.get(0).getY(),
					players.get(0).getWidth(), players.get(0).getHeight())) {
				//TODO
				System.out.println("Crushed");
			}
			for (Projectile p : projectiles) {
				if (b.getBounds().intersects(
						p.getX(), p.getY(), p.getImg().getWidth(), p.getImg().getHeight()
				)) {
					projectiles.remove(p);
					if (b.getSize() >= 15) {
						balls.add(new Ball(b.getX(), b.getY(), ballSpeed, -5, b.getSize() / 2));
						balls.add(new Ball(b.getX(), b.getY(), -ballSpeed, -5, b.getSize() / 2));
					}
					balls.remove(b);
					System.out.println("HIT");
				}
			}
		}
	}

	/**
	 * Handle the movement of balls.
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
				b.setSpeedY(b.getBounceSpeed());
			}
		}
	}

	/**
	 * Handles movement of projectiles.
	 */
	public void shootProjectiles() {
		for (Projectile p : projectiles) {
			if (p.getY() <= 0) {
				projectiles.remove(p);
			} else {
				p.shoot();
			}
		}
	}

	/**
	 * Paint all views.
	 */
	public void paint() {
		// Clear canvas.
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		players.get(0).draw(gc);
		for (Projectile p : projectiles) {
			gc.drawImage(p.getImg(), p.getX(), p.getY());
		}
		for (Ball b: balls) {
			b.draw(gc);
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
				shootProjectiles();
				detectCollisions();
				paint();
				for (Player player : players) {
					player.move();
				}
				//collide();
			}
		}.start();
	}

	/**
	 * Handler for key presses.
	 */
	private class KeyPressHandler implements EventHandler<KeyEvent> {
		public void handle(KeyEvent key) {
			switch (key.getCode()) {
				case RIGHT:
					players.get(0).setSpeed(6);
					break;
				case LEFT:
					players.get(0).setSpeed(-6);
					break;
				case SPACE:
					shoot(players.get(0));
					break;
				default:
					players.get(0).setSpeed(0);
					break;
			}
		}
	}

}
