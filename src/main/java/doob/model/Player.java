package doob.model;

import doob.model.powerup.PowerUp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

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
   * 
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

  /**
   * Whether a player collides with another object.
   * 
   * @param other
   *          the other object.
   * @return whether it collides.
   */
  public boolean collides(Collidable other) {
    if (other instanceof Ball) {
      // a player only collides with a ball
      Ball b = (Ball) other;
      return b.getBounds().intersects(x, y, width, height);
    } else if (other instanceof PowerUp) {
      PowerUp p = (PowerUp) other;
      Rectangle rect = new Rectangle(p.getLocationX(), p.getLocationY(), 30, 30);
      return rect.intersects(x, y, width, height);
    }
    return false;
  }

  /**
   * Draws the player sprites. There is a standing png image for standing still and two moving gif
   * images for moving left and right.
   * @param g 
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

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Player{"
            + "x=" + x
            + ", lives=" + lives + '}';
  }
}
