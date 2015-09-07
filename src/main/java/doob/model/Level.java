package doob.model;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;

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
    
    private Wall right;
    private Wall left;
    private Wall ceiling;
    private Wall floor;
	
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
						ballSize
						));
		}
	}
	
	/**
	 * 
	 */
	public void projectileCeilingCollision() {
	  int projHitIndex = -1;
		for (int i = 0; i < projectiles.size(); i++) {
		  Projectile p = projectiles.get(i);
			if (p.getY() <= 0) {
			  projHitIndex = i;
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
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//TODO Player should die, but for now it collides too fast, 
					//boundaries should be modified.				  
					System.out.println("Crushed");
				}
			}
			for (int j = 0; j < projectiles.size(); j++) {
			  Projectile p = projectiles.get(j);
				if (p.collides(b)) {
					projHitIndex = j;
					if (b.getSize() >= 15) {
						Ball[] res = b.split();
						balls.add(res[0]);
						balls.add(res[1]);
					}
					ballHitIndex = i;
					System.out.println("HIT");
				}
			}
		}
		if (ballHitIndex != -1) balls.remove(ballHitIndex);
		if (projHitIndex != -1) projectiles.remove(projHitIndex);
	}
	
	/**
	 * Function which checks if balls collide with walls.
	 */
	public void ballWallCollision() {
		for (Ball b: balls) {
			if (b.getBounds().intersects(floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight())) {
				System.out.println("Hit the floor");
				b.setSpeedY(b.getBounceSpeed());
			}
			else if (b.getBounds().intersects(left.getX(), left.getY(), left.getWidth(), left.getHeight())) {
				System.out.println("Hit the left wall");
				b.setSpeedX(b.getBallSpeed());				
			}
			else if (b.getBounds().intersects(right.getX(), right.getY(), right.getWidth(), right.getHeight())) {
				System.out.println("Het the right wall");
				b.setSpeedX(-1*(b.getBallSpeed()));
			}
			//TODO Balls can collide with the ceiling, and a special bonus has to be added.
		}
	}
	
	public void playerWallCollision() {
		for (Player p: players) {
			if(p.getX() <= left.getX()) {
				//Player wants to pass the left wall
				p.setX(0);
			}
			else if (p.getX() + p.getWidth() >= right.getX()) {
				//Player wants to pass the right wall
				p.setX((int) canvas.getWidth() - p.getWidth());
			}
		}
	}
	
	//TODO Collisionfunctions should be moved
	
	/**
	 * Loops through every object in the game to detect collisions.
	 */
	public void detectCollisions() {
		projectileCeilingCollision();
		ballProjectileCollision();
		playerWallCollision();
		ballWallCollision();
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
	public void update() {
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

    public Wall getRight() {
		return right;
	}

	public void setRight(Wall right) {
		this.right = right;
	}

	public Wall getLeft() {
		return left;
	}

	public void setLeft(Wall left) {
		this.left = left;
	}

	public Wall getCeiling() {
		return ceiling;
	}

	public void setCeiling(Wall ceiling) {
		this.ceiling = ceiling;
	}
	
	public Wall getFloor() {
		return floor;
	}
	
	public void setFloor(Wall floor) {
		this.floor = floor;
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

        public void setCanvas(Canvas canvas) {
            this.canvas = canvas;
        }

        public Builder setBalls(ArrayList<Ball> balls) {
            this.balls = balls;
            return this;
        }

        public Builder setPlayers(ArrayList<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setPlayerSpeed(int playerSpeed) {
            this.playerSpeed = playerSpeed;
            return this;
        }

        public Level build() {
            Level level = new Level(canvas);
    		Wall right = new Wall( (int) canvas.getWidth(), 0, 1, (int) canvas.getHeight());
    		Wall left = new Wall( 0, 0, -1, (int) canvas.getHeight());
    		Wall ceiling = new Wall(0, 0, (int) canvas.getWidth(), -1);
    		Wall floor = new Wall(0, (int) canvas.getHeight(), (int) canvas.getWidth(), 1);
    		
    		level.setLeft(left);
    		level.setRight(right);
    		level.setCeiling(ceiling);
    		level.setFloor(floor);
    		
            level.setBalls(balls);
            level.setPlayers(players);
            level.setPlayerSpeed(playerSpeed);
            return level;
        }

	}

}
