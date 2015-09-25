package doob.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.google.common.reflect.ClassPath;
import doob.DLog;
import doob.model.powerup.PowerUp;
import doob.model.powerup.PowerUpChance;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import doob.App;

/**
 * Level class, created from LevelFactory.
 */
public class Level {

  private Canvas canvas;
  private GraphicsContext gc;

  private ArrayList<Ball> balls;
  private ArrayList<Projectile> projectiles;
  private ArrayList<Player> players;
  private ArrayList<Wall> walls;
  private int score = 0;
  private double currentTime;
  private int time;
  private int playerSpeed = PLAYERSPEED;
  public static final int SHOOTSPEED = 12;
  public static final int STARTHEIGHT = 200;
  public static final int BALLSIZE = 96;
  public static final int PLAYERSPEED = 3;
  public static final int PROJECTILE_START_SPEED = 12;

  private static int projectileSpeed = PROJECTILE_START_SPEED;

  private Wall right;
  private Wall left;
  private Wall ceiling;
  private Wall floor;

  private boolean endlessLevel;
  private int flag = 0;
  private boolean ballFreeze;
  private boolean projectileFreeze;

  private ArrayList<Class<?>> availablePowerups;
  private ArrayList<PowerUp> powerupsOnScreen;
  private ArrayList<PowerUp> activePowerups;
  private ArrayList<Wall> checkedWalls;

  /**
   * Initialize javaFx.
   * 
   * @param canvas
   *          the canvas to be drawn upon.
   */
  public Level(Canvas canvas) {
	this.checkedWalls = new ArrayList<Wall>();
    this.endlessLevel = true;
    this.canvas = canvas;
    ballFreeze = false;
    projectileFreeze = false;
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
    initPowerups();
  }

  /**
   * Create a new projectile and move it.
   * 
   * @param player
   *          the player that shoots the projectile.
   */
  public void shoot(Player player) {
    if (projectiles.size() < 1) {
      projectiles.add(new Spike(player.getX() + player.getWidth() / 2, canvas.getHeight(),
              PROJECTILE_START_SPEED));
      DLog.info("Player shot projectile.", DLog.Type.PLAYER_INTERACTION);
    }
  }

  /**
   * For testing purposes, an endless level.
   */
  public void endlessLevel() {
    if (endlessLevel && balls.size() == 0) {
      balls.add(new Ball(0, STARTHEIGHT, 3, 0, BALLSIZE));
    }
  }

  /**
   * Function that checks a collision between a projectile and a ceiling.
   */
  public void projectileCeilingCollision() {
    int projHitIndex = -1;
    for (int i = 0; i < projectiles.size(); i++) {
      Projectile p = projectiles.get(i);
      if (p.getY() <= 0) {
        projHitIndex = i;
      } else {
    	  for (Wall w : walls) {
    		  if (w.collides(p)) {
    			  projHitIndex = i; 
    		  }
    	  }
      }
    }
    if (projHitIndex != -1) {
      if (projectileFreeze) {
    	  projectiles.get(projHitIndex).setState(Projectile.State.FROZEN);
      } else {
    	  projectiles.remove(projHitIndex);
      }
    }
  }

  /**
   * Checks for each ball if it collides with a projectile.
   */
  public void ballProjectileCollision() {
    int ballHitIndex = -1;
    int projHitIndex = -1;
    Ball[] res = null;
    for (int i = 0; i < balls.size(); i++) {
      Ball b = balls.get(i);
      for (int j = 0; j < projectiles.size(); j++) {
        Projectile p = projectiles.get(j);
        if (p.collides(b)) {
          projHitIndex = j;
          if (b.getSize() >= 32) {
            DLog.info(b.toString() + " splits", DLog.Type.COLLISION);
            res = b.split();
          } else {
            DLog.info(b.toString() + " disappears", DLog.Type.COLLISION);
          }
          ballHitIndex = i;
          players.get(0).incrScore(100);
          processPowerups(b.getX(), b.getY()); // possible spawn a powerup at location of ball
        }
      }
    }
    if (ballHitIndex != -1) {
      balls.remove(ballHitIndex);
    }
    if (projHitIndex != -1) {
      projectiles.remove(projHitIndex);
    }
    if (res != null) {
      balls.add(res[0]);
      balls.add(res[1]);
    }
  }

  /**
   * Function which checks if balls collide with walls.
   */
  public void ballWallCollision() {
	  int ballHitIndex = -1;
    for (int i = 0; i < balls.size(); i++) {
    	Ball b = balls.get(i);
      if (b.collides(floor)) {
        b.setSpeedY(b.getBounceSpeed());
      }
      for (Wall w : walls) {
    	  if (b.collides(w)) {
    		  if (w.isMoveable()) {
    			  ballHitIndex = i;
    			  // TODO add points
    		  } else {
    			  double speedX = b.getSpeedX();
    			  b.setSpeedX(-1 * speedX);
    		  }
    	  }
      }
      for (Wall w : checkedWalls) {
    	  if (b.collides(w)) {
    		  if (w.isMoveable()) {
    			  ballHitIndex = i;
    			  // TODO add points
    		  } else {
    			  double speedX = b.getSpeedX();
    			  b.setSpeedX(-1 * speedX);
    		  }
    	  }
      }
      if (ballHitIndex != -1) {
    	  balls.remove(ballHitIndex);
    	  ballHitIndex = -1;
      }
    }
  }

  /**
   * Function that detects collisions between players and walls.
   */
  public void playerWallCollision() {
	  for (Player p : players) {
	      if (p.getX() <= left.getX()) {
	        // Player wants to pass the left wall
	        p.setX(0);
	      } else if (p.getX() + p.getWidth() >= right.getX()) {
	        // Player wants to pass the right wall
	        p.setX((int) canvas.getWidth() - p.getWidth());
	      }
	      playerWallHelper(walls, p);
	      playerWallHelper(checkedWalls, p);
	           
	  }
  }
  
  /**
   * Helper function for playerwallcollision
   * @param walls to check
   * @param p the player
   */
  public void playerWallHelper(ArrayList<Wall> walls, Player p) {
	  for (Wall w : walls) {
	   	  if (p.collides(w)) {
	   		  int xSpeed = p.getSpeed();
	   		  if (xSpeed > 0) {
		   		  if (p.getX() + p.getWidth() >= w.getX()) {
		   			p.setX(w.getX() - 10 - p.getWidth());
		   		  }
	   		  } else if (xSpeed < 0) {
	   			if (p.getX() <= w.getX() + w.getWidth()) {
		   			p.setX(w.getX() + w.getWidth() + 10);
		   		}
	   		  } else {
	   			  if (p.getX() == w.getX() + w.getWidth()) {
	   				  p.setX(p.getX() + 1);
	   			  } else { 
	   				  p.setX(p.getX() - 1); }
	   		  }
	   	  }
      } 
  }
  
  /**
   * Function that checks if all balls in a compartment are gone.
   * Compartment should open.
   */
  public void ballWallCheck() {
	  if (walls.size() > 2) {
		  for (int i = 0; i < walls.size() - 1; i++) {
			  //first wall is the left wall
			  Wall left = walls.get(i);
			  Wall right = walls.get(i + 1);
			  if (spaceEmpty(left, right)) {
				  if (walls.size() > 2) {
					  right.setPlayerwalk(true);
					  //right.setHeight(right.getHeight() - 250);
					  checkedWalls.add(right);
					  walls.remove(right);
					  DLog.info("Wall opened", DLog.Type.PLAYER_INTERACTION);
				  } else {
					  walls.remove(right);
					  DLog.info("Wall removed", DLog.Type.PLAYER_INTERACTION);
				  }
			  }	
		  }
	  }
  }
  
  /**
   * Function that returns true if there are no balls between two walls.
   * @param w1 Wall one
   * @param w2 Wall two
   * @return boolean if the space is empty
   */
  public boolean spaceEmpty(Wall w1, Wall w2) {	
	  int w1x = w1.getX() + w1.getWidth();
	  int w2x = w2.getX();
	  if (balls.size() > 0) {
		  for (Ball b : balls) {
			  if (b.getX() + b.getSize() >= w1x && b.getX() <= w2x) {
				  return false;
			  }
		  }
		  this.flag++;
		  if (this.flag > 1) {
			  //this flag is because of the first second in the game, 
			  //in which the balls are not yet correct or something like that.
			  this.flag = 0;
			  return true;	
		  }
	  }
	  return false;
  }
  
  /**
   * Function that detects if a player is hit by a ball.
   * @return boolean if the player is hit.
   */
  public boolean ballPlayerCollision() {
    boolean res = false;
    for (Ball b : balls) {
      for (Player p : players) {
        if (p.collides(b)) {
          res = true;
          DLog.info(p.toString() + " is hit by a ball", DLog.Type.COLLISION);
        }
      }
    }
    return res;
  }
  
  /**
   * Function that detects if a player is hit by the ceiling.
   * @return boolean if the player is hit.
   */
  public boolean playerCeilingCollision() {
	    boolean res = false;
	    for (Wall w : walls) {
	      for (Player p : players) {
	        if (p.collides(w) && w.isMoveable()) {
	          res = true;
	          DLog.info(p.toString() + " is hit by the ceiling", DLog.Type.COLLISION);
	        }
	      }
	    }
	    return res;
	  }
  /**
   * Checks for collisions between powerups and players.
   */
  public void powerupPlayerCollision() {
    PowerUp toRemove = null;
    for (PowerUp powerup : powerupsOnScreen) {
      for (Player p : players) {
        if (p.collides(powerup)) {
          DLog.info(p.toString() + " is hit by a powerup", DLog.Type.COLLISION);
          powerup.onActivate(this, p);
          toRemove = powerup;
          activePowerups.add(powerup);
        }
      }
    }
    if (toRemove != null) {
      powerupsOnScreen.remove(toRemove);
    }
  }

  /**
   * Loops through every object in the game to detect collisions.
   */
  public void detectCollisions() {
    projectileCeilingCollision();
    ballProjectileCollision();
    playerWallCollision();
    ballWallCollision();
    powerupPlayerCollision();
  }

  /**
   * Handle the movement of balls.
   */
  public void moveBalls() {
    if (ballFreeze) {
      return;
    }
    for (Ball b : balls) {
      b.move();
    }
  }
  
  /**
   * Handle the movements of walls.
   */
  public void moveWalls() {
	  for (Wall w : walls) {
		  if (w.isMoveable()) {
			  w.move();
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
      p.draw(gc);
    }
    for (Ball b : balls) {
      b.draw(gc);
    }
    for (Wall w : walls) {
    	w.draw(gc);
    }
    for (Wall w : checkedWalls) {
    	w.draw(gc);
    }
    for (PowerUp powerup : powerupsOnScreen) {
      gc.drawImage(powerup.getSpriteImage(), powerup.getLocationX(), powerup.getLocationY());
    }
  }

  /**
   * Timer for animation.
   */
  public void update() {
    for (Projectile projectile : projectiles) {
      if (!(projectile.getState() == Projectile.State.FROZEN && projectileFreeze)) {
        projectile.move();
      }
      projectile.draw(gc);
    }
    // endlessLevel();
    detectCollisions();
    ballWallCheck();
    moveBalls();
    moveWalls();
    paint();
    currentTime -= 1;
    for (Player player : players) {
      player.move();
    }
    ArrayList<PowerUp> toRemoveWait = new ArrayList<PowerUp>();
    for (PowerUp powerup : powerupsOnScreen) { // move powerups down
      if (powerup.getLocationY() < floor.getY() - 30) {
        powerup.setLocationY(powerup.getLocationY() + 2);
      }
      powerup.tickWait();
      if (powerup.getCurrentWaitTime() <= 0) {
        toRemoveWait.add(powerup);
      }
    }
    for (PowerUp p : toRemoveWait) {
      powerupsOnScreen.remove(p);
    }
    ArrayList<PowerUp> toRemove = new ArrayList<PowerUp>();
    for (PowerUp powerUp : activePowerups) { // deactivate active powerups, if needed
      powerUp.tickActive();
      if (powerUp.getActiveTime() <= 0) {
        powerUp.onDeactivate(this);
        toRemove.add(powerUp);
      }
    }
    for (PowerUp p : toRemove) {
      activePowerups.remove(p);
    }
  }
  
  /**
   * Function which is triggered if the player is hit by a ball.
   */
  public void crushed() {
    Player p = players.get(0);
    p.setLives(p.getLives() - 1);
    currentTime = time;
  }

  /**
   * Determines whether a powerup will fall down.
   * @param locationX x where powerup should spawn
   * @param locationY y where powerup should spawn
   */
  public void processPowerups(double locationX, double locationY) {
    Random random = new Random();
    for (Class<?> powerup : availablePowerups) {
      double rand = random.nextDouble();
      PowerUpChance chanceAnnotation = powerup.getAnnotation(PowerUpChance.class);
      if (rand < chanceAnnotation.chance()) {
        // drop powerup
        try {
          PowerUp p = (PowerUp) powerup.newInstance();
          Image sprite = new Image(p.spritePath());
          p.setSpriteImage(sprite);
          p.setLocationX(locationX);
          p.setLocationY(locationY);
          powerupsOnScreen.add(p);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Looks up all available powerups.
   */
  public void initPowerups() {
    availablePowerups = new ArrayList<Class<?>>();
    powerupsOnScreen = new ArrayList<PowerUp>();
    activePowerups = new ArrayList<PowerUp>();
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();

    try {
      for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
        if (info.getName().startsWith("doob.model.powerup")) {
          final Class<?> clazz = info.load();
          if (clazz.getSuperclass() != null && clazz.getSuperclass().equals(PowerUp.class)) {
            availablePowerups.add(clazz);
          }
          // do something with your clazz
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Game lost, return to menu.
   */
  public void gameOver() {
    DLog.info("Game over!", DLog.Type.STATE);
    App.loadScene("/fxml/menu.fxml");
  }

  /**
   * Draw a textimage on the canvas.
   * @param i image to draw
   */
  public void drawText(Image i) {
    gc.drawImage(i, canvas.getWidth() / 2 - i.getWidth() / 2,
            canvas.getHeight() / 2 - i.getHeight());
  }

  public Wall getRight() {
    return right;
  }

  public void setRight(Wall right) {
    this.right = right;
  }

  public ArrayList<PowerUp> getActivePowerups() {
    return activePowerups;
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

  public static int getProjectileSpeed() {
    return projectileSpeed;
  }

  public static void setProjectileSpeed(int projectileSpeed) {
    Level.projectileSpeed = projectileSpeed;
  }
  
  /**
   * Returns the score of a player.
   * @param player to get the score of
   * @return int score
   */
  public int getScore(int player) {
    return players.get(player).getScore();
  }

  public double getCurrentTime() {
    return currentTime;
  }

  public int getTime() {
	return time;
}

public void setTime(int time) {
	this.time = time;
}

public void setCurrentTime(double currentTime) {
    this.currentTime = currentTime;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public ArrayList<Ball> getBalls() {
    return balls;
  }

  public ArrayList<Wall> getWalls() {
	return walls;
}

public void setWalls(ArrayList<Wall> walls) {
	this.walls = walls;
}

public int getPlayerSpeed() {
	return playerSpeed;
}

public void setPlayerSpeed(int playerSpeed) {
	this.playerSpeed = playerSpeed;
}

  public void setFlag(int f){
    this.flag = f;
  }

  public void setPowerupsOnScreen(ArrayList<PowerUp> l){
    this.powerupsOnScreen = l;
  }

  public ArrayList<PowerUp> getPowerupsOnScreen(){return this.powerupsOnScreen; }


  public void setActivePowerups(ArrayList<PowerUp> l){
    this.activePowerups = l;
  }

  public boolean isBallFreeze() {
	return ballFreeze;
}

public void setBallFreeze(boolean ballFreeze) {
	this.ballFreeze = ballFreeze;
}

public boolean isProjectileFreeze() {
	return projectileFreeze;
}

public void setProjectileFreeze(boolean projectileFreeze) {
	this.projectileFreeze = projectileFreeze;
}

/**
   * Handler for key presses.
   */
  private class KeyPressHandler implements EventHandler<KeyEvent> {
    private KeyCode last = KeyCode.SPACE;
    public void handle(KeyEvent key) {
      switch (key.getCode()) {
      case RIGHT:
        players.get(0).setSpeed(players.get(0).getMoveSpeed());
        if (last != KeyCode.RIGHT) {
          DLog.info("Player direction changed to right.", DLog.Type.PLAYER_INTERACTION);
          last = KeyCode.RIGHT;
        }
        break;
      case LEFT:
        players.get(0).setSpeed(-players.get(0).getMoveSpeed());
        if (last != KeyCode.LEFT) {
          DLog.info("Player direction changed to left.", DLog.Type.PLAYER_INTERACTION);
          last = KeyCode.LEFT;
        }
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
   * Class that assists in building level.
   */
  public static class Builder {

    private Canvas canvas;
    private ArrayList<Ball> balls;
    private ArrayList<Player> players;
    private ArrayList<Wall> walls;
    private int playerSpeed = PLAYERSPEED;
    private int time;

    /**
     * Constructor.
     */
    public Builder() {
      super();
    }

    public void setCanvas(Canvas canvas) {
      this.canvas = canvas;
    }
    public void setTime(int time) {
    	this.time = time;
    }

    /**
     * Balls Setter.
     * @param balls balls
     * @return builder
     */
    public Builder setBalls(ArrayList<Ball> balls) {
      this.balls = balls;
      return this;
    }

	public void setWalls(ArrayList<Wall> walls) {
		this.walls = walls;
	}

	/**
     * Player Setter.
     * @param players players
     * @return builder
     */
    public Builder setPlayers(ArrayList<Player> players) {
      this.players = players;
      return this;
    }

    /**
     * Builds the level.
     * @return level
     */
    public Level build() {
      Level level = new Level(canvas);
      Wall right = new Wall((int) canvas.getWidth(), 0, 1, (int) canvas.getHeight());
      Wall left = new Wall(0, 0, -1, (int) canvas.getHeight());
      Wall ceiling = new Wall(0, 0, (int) canvas.getWidth(), -1);
      Wall floor = new Wall(0, (int) canvas.getHeight(), (int) canvas.getWidth(), 1);

      level.setLeft(left);
      level.setRight(right);
      level.setCeiling(ceiling);
      level.setFloor(floor);

      Collections.sort(walls, new Comparator<Wall>() {
        @Override
        public int compare(Wall w1, Wall w2) {
          return  Integer.compare(w1.getX(), w2.getX());
        }
      });

      walls.add(right);
      walls.add(0, left);
      
      level.setBalls(balls);
      level.setPlayers(players);
      level.setPlayerSpeed(playerSpeed);
      level.setWalls(walls);
      level.setTime(time);
      level.setCurrentTime(time);
      return level;
    }
  }

}
