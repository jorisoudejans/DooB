package doob.model;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * Level class, created from TODO LevelFactory.
 */
public class Level {

	private Canvas canvas;
	private GraphicsContext gc;

	private ArrayList<Ball> balls;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Player> players;
	private int shootSpeed = 12;
	private int startHeight = 200;
	private int ballSize = 100;
	
	private boolean endlessLevel;

	/**
	 * Initialize javaFx.
	 * @param canvas the canvas to be drawn upon.
	 */
	public Level(Canvas canvas) {
		this.endlessLevel = true;
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(new KeyPressHandler());

		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				if (key.getCode() != KeyCode.SPACE) {
					players.get(0).setSpeed(0);
				}
			}
		});

		canvas.requestFocus();
		balls = new ArrayList<Ball>();
		balls.add(
				new Ball(
						0,
						startHeight,
						3,
						0,
						ballSize,
						(int) canvas.getWidth(),
						(int) canvas.getHeight())
		);
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
	 * Create a new projectile and move it.
	 * @param player the player that shoots the projectile.
	 */
	public void shoot(Player player) {
		if(projectiles.size() < 1) {
			//TODO there can be a powerup for which there can be more than one projectile.
			projectiles.add(new Spike(player.getX(), canvas.getHeight(), shootSpeed));
		}
	}

	/**
	 * Loops through every object in the game to detect collisions.
	 */
	public void detectCollisions() {
		//Endless level
		if (endlessLevel && balls.size() == 0) {
			balls.add(new Ball(0,
						startHeight,
						3,
						0,
						ballSize,
						(int) canvas.getWidth(),
						(int) canvas.getHeight()));
		}
		for (Projectile p : projectiles) {
			//TODO If projectile collides with wall, it should be removed. 
			//Wall object has to be created.
			if (p.getY() <= 0) {//canvas.getHeight()) {
				projectiles.remove(p);
			}
		}
		for (Ball b : balls) {
			for (Player p : players) {
				if (p.collides(b)) {
					//TODO Player should die, but for now it collides too fast, boundaries should be modified.
					System.out.println("Crushed");
				}
			}
			for (Projectile p : projectiles) {
				if (p.collides(b)) {
					projectiles.remove(p);
					if (b.getSize() >= 15) {
						balls.add(new Ball(b.getX(), b.getY(), 3, -5, b.getSize() / 2, (int) canvas.getWidth(), (int) canvas.getHeight()));
						balls.add(new Ball(b.getX(), b.getY(), -3, -5, b.getSize() / 2, (int) canvas.getWidth(), (int) canvas.getHeight()));
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
			b.move();
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
			p.draw(gc);
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
				for (Drawable drawable : projectiles) {
					drawable.move();
					drawable.draw(gc);
				}
				detectCollisions();
				moveBalls();
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

	public boolean isEndlessLevel() {
		return endlessLevel;
	}

	public void setEndlessLevel(boolean endlessLevel) {
		this.endlessLevel = endlessLevel;
	}

}
