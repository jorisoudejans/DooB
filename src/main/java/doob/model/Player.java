package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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
  
  public static final int LIVES = 5;
  public static final int START_SPEED = 4;
  public static final int BOUNCE_BACK_DISTANCE = 10;

  private Image imageStand;
  private Image imageLeft;
  private Image imageRight;

  private State state;

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

    imageStand = imageS;
    imageLeft = imageL;
    imageRight = imageR;
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

  public void setY(int y) {
    this.y = y;
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
  
  public void incrScore(int scoreIncr) {
	  score = score + scoreIncr;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  /**
   * Decreases lives by one.
   */
  public void die() {
    lives--;
  }

  @Override
  public String toString() {
    return "Player{"
            + "x=" + x
            + ", lives=" + lives + '}';
  }
}
