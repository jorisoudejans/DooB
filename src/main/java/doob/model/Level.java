package doob.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
 * Level class, created from TODO LevelFactory.
 */
public class Level {

  private Canvas canvas;
  private GraphicsContext gc;

  private ArrayList<Ball> balls;
  private ArrayList<Projectile> projectiles;
  private ArrayList<Player> players;
  private int score = 0;
  private double currentTime = TIME;
  private int playerSpeed = PLAYERSPEED;
  public static final int SHOOTSPEED = 12;
  public static final int STARTHEIGHT = 200;
  public static final int BALLSIZE = 96;
  public static final int PLAYERSPEED = 3;
  public static final double TIME = 2000;

  private Wall right;
  private Wall left;
  private Wall ceiling;
  private Wall floor;

  private boolean endlessLevel;

  private ArrayList<Class<?>> availablePowerups;
  private ArrayList<PowerUp> powerupsOnScreen;
  private ArrayList<PowerUp> activePowerups;

  /**
   * Initialize javaFx.
   * 
   * @param canvas
   *          the canvas to be drawn upon.
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
      // TODO there can be a powerup for which there can be more than one projectile.
      projectiles.add(new Spike(player.getX() + player.getWidth() / 2, canvas.getHeight(),
          SHOOTSPEED));
      DLog.i("Player shot projectile.", DLog.Type.PLAYER_INTERACTION);
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
    if (projHitIndex != -1)
      projectiles.remove(projHitIndex);
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
            DLog.i(b.toString() + " splits", DLog.Type.COLLISION);
            res = b.split();
          } else {
            DLog.i(b.toString() + " disappears", DLog.Type.COLLISION);
          }
          ballHitIndex = i;
          score += 100;
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
    for (Ball b : balls) {
      if (b.collides(floor)) {
        // System.out.println("Hit the floor");
        b.setSpeedY(b.getBounceSpeed());
      } else if (b.collides(left)) {
        // System.out.println("Hit the left wall");
        b.setSpeedX(-b.getSpeedX());
      } else if (b.collides(right)) {
        // System.out.println("Het the right wall");
        b.setSpeedX(-b.getSpeedX());
      }
      // TODO Balls can collide with the ceiling, and a special bonus has to be added.
    }
  }

  public void playerWallCollision() {
    for (Player p : players) {
      if (p.getX() <= left.getX()) {
        // Player wants to pass the left wall
        p.setX(0);
      } else if (p.getX() + p.getWidth() >= right.getX()) {
        // Player wants to pass the right wall
        p.setX((int) canvas.getWidth() - p.getWidth());
      }
    }
  }

  public boolean ballPlayerCollision() {
    boolean res = false;
    for (Ball b : balls) {
      for (Player p : players) {
        if (p.collides(b)) {
          res = true;
          DLog.i(p.toString() + " is hit by a ball", DLog.Type.COLLISION);
        }
      }
    }
    return res;
  }

  public void powerupPlayerCollision() {
    PowerUp toRemove = null;
    for (PowerUp powerup : powerupsOnScreen) {
      for (Player p : players) {
        if (p.collides(powerup)) {
          DLog.i(p.toString() + " is hit by a powerup", DLog.Type.COLLISION);
          powerup.onActivate(this);
          toRemove = powerup;
          activePowerups.add(powerup);
        }
      }
    }
    if (toRemove != null) {
      powerupsOnScreen.remove(toRemove);
    }
  }

  // TODO Collisionfunctions should be moved

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
    for (Ball b : balls) {
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
    for (Ball b : balls) {
      b.draw(gc);
    }
    for (PowerUp powerup : powerupsOnScreen) {
      gc.drawImage(powerup.getSpriteImage(), powerup.getLocationX(), powerup.getLocationY());
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
    // endlessLevel();
    detectCollisions();
    moveBalls();
    paint();
    currentTime -= 1;
    for (Player player : players) {
      player.move();
    }
    ArrayList<PowerUp> toRemoveWait = new ArrayList<PowerUp>();
    for (PowerUp powerup : powerupsOnScreen) { // move powerups down
      if (powerup.getLocationY() < floor.getY()-30) {
        powerup.setLocationY(powerup.getLocationY()+2);
      }
      powerup.tickWait();
      if (powerup.getWaitTime() <= 0) {
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

  public void crushed() {
    Player p = players.get(0);
    p.setLives(p.getLives() - 1);
    currentTime = TIME;
  }

  /**
   * Determines whether a powerup will fall down
   * @param locationX x where powerup should spawn
   * @param locationY y where powerup should spawn
   */
  public void processPowerups(double locationX, double locationY) {
    Random random = new Random();
    for(Class<?> powerup : availablePowerups) {
      double rand = random.nextDouble();
      PowerUpChance chanceAnnotation = powerup.getAnnotation(PowerUpChance.class);
      if (rand < chanceAnnotation.chance()) {
        // drop powerup
        try {
          PowerUp p = (PowerUp)powerup.newInstance();
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
   * Looks up all available powerups
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
    DLog.i("Game over!", DLog.Type.STATE);
    App.loadScene("/fxml/menu.fxml");
  }

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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public double getCurrentTime() {
    return currentTime;
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

  /**
   * Handler for key presses.
   */
  private class KeyPressHandler implements EventHandler<KeyEvent> {
    private KeyCode last = KeyCode.SPACE;
    public void handle(KeyEvent key) {
      switch (key.getCode()) {
      case RIGHT:
        players.get(0).setSpeed(playerSpeed);
        if (last != KeyCode.RIGHT) {
          DLog.i("Player direction changed to right.", DLog.Type.PLAYER_INTERACTION);
          last = KeyCode.RIGHT;
        }
        break;
      case LEFT:
        players.get(0).setSpeed(-playerSpeed);
        if (last != KeyCode.LEFT) {
          DLog.i("Player direction changed to left.", DLog.Type.PLAYER_INTERACTION);
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
   * Builder class.
   */
  public static class Builder {

    private Canvas canvas;
    private ArrayList<Ball> balls;
    private ArrayList<Player> players;
    private int playerSpeed = PLAYERSPEED;

    /**
     * Constructor.
     */
    public Builder() {
      super();
    }

    public void setCanvas(Canvas canvas) {
      this.canvas = canvas;
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
     * Playerspeed Setter.
     * @param playerSpeed playerspeed
     * @return builder
     */
    public Builder setPlayerSpeed(int playerSpeed) {
      this.playerSpeed = playerSpeed;
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

      level.setBalls(balls);
      level.setPlayers(players);
      level.setPlayerSpeed(playerSpeed);
      return level;
    }

  }

}
