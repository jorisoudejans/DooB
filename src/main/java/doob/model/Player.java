package doob.model;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;

/**
 * Player class, acts as both model and view.
 */
public class Player implements Collidable, Drawable {

  private int width;
  private int height;
  private int x;
  private int y;
  private int speed;
  private int moveSpeed;
  private int lives;
  private int score;
  
  private ControlKeys controlKeys;
  
  public static final int LIVES = 5;
  public static final int DOUBLE_LIVES = 10;
  public static final int START_SPEED = 4;
  public static final int BOUNCE_BACK_DISTANCE = 10;

  private Image imageStand;
  private Image imageLeft;
  private Image imageRight;

  private State state;
  private ArrayList<Projectile> projectiles;

  /**
   * Possible player states.
   */
  public enum State {
    NORMAL,
    INVULNERABLE
  }

  /**
   * Constructor for a player with initial location x.
   * @param x
   *          initial x location.
   * @param y
   *          initial y location.
   * @param width
   *          view width.
   * @param height
   *          view height.
   * @param imageS
   *          standing image.
   * @param imageL
   *          moving left image.
   * @param imageR
   *          moving right image.
   */
  public Player(int x, int y, int width, int height, Image imageS, Image imageL, Image imageR) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.speed = 0;
    this.moveSpeed = START_SPEED;
    this.score = 0;
    this.lives = LIVES;
    this.state = State.NORMAL;
    controlKeys = null;
    imageStand = imageS;
    imageLeft = imageL;
    imageRight = imageR;
    projectiles = new ArrayList<Projectile>();
  }

  @Override
  public Shape getBounds() {
    return new Rectangle(x, y, width, height);
  }

  /**
   * Draws the player sprites. There is a standing png image for standing still and two moving gif
   * images for moving left and right.
   * @param g graphicsContext to draw on.
   */
  public void draw(GraphicsContext g) {
    if (g == null) {
      throw new IllegalStateException("GraphicsContext can not be null");
    }
    Image image = imageStand;
    if (speed > 0) {
      image = imageRight;
    }
    if (speed < 0) {
      image = imageLeft;
    }
    g.drawImage(image, x, y);
  }

  public int getWidth() {
    return width;
  }

  /**
   * Moves the player by adding a certain speed to it's x position.
   */
  public void move() {
    this.x = x + speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public int getSpeed() {
    return speed;
  }

  public int getMoveSpeed() {
    return moveSpeed;
  }

  public void setMoveSpeed(int moveSpeed) {
    this.moveSpeed = moveSpeed;
  }

  public int getLives() {
    return lives;
  }

  public void setLives(int lives) {
    this.lives = lives;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Increments score by amount.
   * @param scoreIncr amount to increment
   */
  public void incrScore(int scoreIncr) {
	  score = score + scoreIncr;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public ControlKeys getControlKeys() {
    return controlKeys;
  }

  public void setControlKeys(ControlKeys controlKeys) {
    this.controlKeys = controlKeys;
  }

  public List<Projectile> getProjectiles() {
	return projectiles;
}

  /**
   * Decreases lives by one.
   */
  public void die() {
    lives--;
  }
  
  public boolean isAlive() {
	  return lives > 0;
  }

  @Override
  public String toString() {
    return "Player{"
            + "x=" + x
            + ", lives=" + lives + '}';
  }


  /**
   * Represents control keys for player.
   */
  public static class ControlKeys {
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode shootKey;
    
    /**
     * the lastKey value is used, suppressed warning.
     */
    @SuppressWarnings("unused")
	private KeyCode lastKey;

    /**
     * Moving action.
     */
    public enum Action {
      NONE, LEFT, SHOOT, RIGHT
    }

    /**
     * Get new instance.
     * @param leftKey left
     * @param rightKey right
     * @param shootKey shoot
     */
    public ControlKeys(KeyCode leftKey, KeyCode rightKey, KeyCode shootKey) {
      this.leftKey = leftKey;
      this.rightKey = rightKey;
      this.shootKey = shootKey;
      this.lastKey = KeyCode.SPACE;
    }

    /**
     * Determines if the key pressed matches a player set key.
     * @param key pressed key
     * @return Action result
     */
    public Action determineAction(KeyCode key) {
      Action action = Action.NONE;
      if (key == rightKey) {
        action = Action.RIGHT;
      } else if (key == leftKey) {
        action = Action.LEFT;
      } else if (key == shootKey) {
        action = Action.SHOOT;
      }
      if (action != Action.NONE) {
        lastKey = key;
      }
      return action;
    }

    /**
     * Determines whether the key pressed moves the character.
     * @param press input key
     * @return true if the key moves the player
     */
    public boolean isMoveKey(KeyCode press) {
      return press == rightKey || press == leftKey;
    }

  }


}
