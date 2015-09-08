package doob.model;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private int playerSpeed = 6;
	
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
		balls.add(new Ball(
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
				50,
				new Image("/image/character1_stand.png"),
				new Image("/image/character1_left.gif"),
				new Image("/image/character1_right.gif")
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
	 * For testing purposes, an endless level.
	 */
	public void endlessLevel() {
		if (endlessLevel && balls.size() == 0) {
			balls.add(new Ball(0,
						startHeight,
						3,
						0,
						ballSize,
						(int) canvas.getWidth(),
						(int) canvas.getHeight()));
		}
	}
	
	/**
	 * 
	 */
	public void projectileCeilingCollision() {
	  int projHitIndex = -1;
		for (int i = 0; i < projectiles.size(); i++) {
			//TODO If projectile collides with wall, it should be removed. 
			//Wall object has to be created.
		  Projectile p = projectiles.get(i);
			if (p.getY() <= 0) {
			  projHitIndex = i;
				//projectiles.remove(p);
			}
		}
		if (projHitIndex != -1) projectiles.remove(projHitIndex);
	}
	
	/**
	 * Checks for each ball if it collides with a projectile.
	 */
	public void ballProjectileCollision() {
	  int ballHitIndex = -1;
	  int projHitIndex = -1;
		for (int i = 0; i < balls.size(); i++) {
		  Ball b = balls.get(i);
			for (Player p : players) {
				if (p.collides(b)) {
					//TODO Player should die, but for now it collides too fast, boundaries should be modified.				  
					System.out.println("Crushed");
				}
			}
			for (int j = 0; j < projectiles.size(); j++) {
			  Projectile p = projectiles.get(j);
				if (p.collides(b)) {
					//projectiles.remove(p);
				  projHitIndex = j;
					if (b.getSize() >= 15) {
						balls.add(new Ball(b.getX(), b.getY(), 3, -5, b.getSize() / 2, (int) canvas.getWidth(), (int) canvas.getHeight()));
						balls.add(new Ball(b.getX(), b.getY(), -3, -5, b.getSize() / 2, (int) canvas.getWidth(), (int) canvas.getHeight()));
					}
					//balls.remove(b);
					ballHitIndex = i;
					System.out.println("HIT");
				}
			}
		}
		if (ballHitIndex != -1) balls.remove(ballHitIndex);
		if (projHitIndex != -1) projectiles.remove(projHitIndex);
	}
	
	/**
	 * Loops through every object in the game to detect collisions.
	 */
	public void detectCollisions() {
		//TODO split in different collision functions.
		projectileCeilingCollision();
		ballProjectileCollision();
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
				endlessLevel();
				detectCollisions();
				moveBalls();
				paint();
				for (Player player : players) {
					player.move();
				}
			}
		}.start();
	}

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    /**
	 * Handler for key presses.
	 */
	private class KeyPressHandler implements EventHandler<KeyEvent> {
		public void handle(KeyEvent key) {
			switch (key.getCode()) {
				case RIGHT:
					players.get(0).setSpeed(playerSpeed);
					break;
				case LEFT:
					players.get(0).setSpeed(-playerSpeed);
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

	/**
	 * Builder class
	 */
	public static class Builder {

        private Canvas canvas;
        private ArrayList<Ball> balls;
        private ArrayList<Player> players;
        private int playerSpeed = 12;

        /**
         * Constructor.
         */
        public Builder() {
            super();
        }

        public Builder setCanvas(Canvas canvas) {
            this.setCanvas(canvas);
            return this;
        }

        public Builder setBalls(ArrayList<Ball> balls) {
            this.setBalls(balls);
            return this;
        }

        public Builder setPlayers(ArrayList<Player> players) {
            this.setPlayers(players);
            return this;
        }

        public Builder setPlayerSpeed(int playerSpeed) {
            this.playerSpeed = playerSpeed;
            return this;
        }

        public Level build() {
            Level level = new Level(canvas);
            level.setBalls(balls);
            level.setPlayers(players);
            level.setPlayerSpeed(playerSpeed);
            return level;
        }

	}

}
