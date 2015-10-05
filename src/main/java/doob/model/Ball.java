package doob.model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Class to represent a ball.
 *
 */
public class Ball implements Collidable, Drawable {

  private double x;
  private double y;
  private double speedX;
  private double speedY;
  private int size;
  private int splitBounce;
  private Color color;

  public static final int SPLITBOUNCE = -4;
  public static final double VERTICALSPEEDFACTOR = 0.2;
  public static final int START_SPEED_X = 2;

  /**
   * Constructor.
   * 
   * @param x
   *          x-coordinate
   * @param y
   *          y-coordinate
   * @param speedX
   *          x-axis speed
   * @param speedY
   *          y-axis speed
   * @param size
   *          the size of the ball
   */
  public Ball(double x, double y, double speedX, double speedY, int size) {
    this.x = x;
    this.y = y;
    this.speedX = speedX;
    this.speedY = speedY;
    this.size = size;
    this.splitBounce = SPLITBOUNCE;
    setColor();
  }
  
  /**
   * Sets the value of the color variable dependent of the size of the ball.
   */
  public void setColor() {
	  switch(size) {
	  case 128: color = Color.RED; break;
	  case 64 : color = Color.GREEN; break;
	  case 32 : color = Color.BLUE; break;
	  case 16 : color = Color.PURPLE; break;
	  default : color = Color.BLACK;
	  }
  }

  /**
   * Draws the ball.
   * 
   * @param graphicsContext
   *          graphicsContext
   */
  public void draw(GraphicsContext graphicsContext) {
	graphicsContext.setFill(color);
    graphicsContext.fillOval(this.getX(), this.getY(), this.getSize(), this.getSize());
    graphicsContext.setFill(Color.BLACK);
  }

  /**
   * Move the ball in a physics simulation.
   */
  public void move() {
    moveHorizontally();
    moveVertically();
    incrSpeedY(VERTICALSPEEDFACTOR);
  }

  /**
   * Split function which handles the creation of new balls after one is hit.
   * 
   * @return A list of new balls.
   */
  public Ball[] split() {
    Ball ball1 = new Ball(this.x, this.y, START_SPEED_X, splitBounce, this.size / 2);
    Ball ball2 = new Ball(this.x, this.y, -START_SPEED_X, splitBounce, this.size / 2);
    Ball[] res = new Ball[2];
    res[0] = ball1;
    res[1] = ball2;
    return res;
  }

  /**
   * Checks if the ball collides with a collidable, either a wall or a projectile.
   * 
   * @return boolean
   * @param other
   *          the collidable the ball collides with
   */
  public boolean collides(Collidable other) {
    if (other instanceof Wall) {
      Wall w = (Wall) other;
      if (this.getBounds().intersects(w.getX(), w.getY(), w.getWidth(), w.getHeight())) {
        return true;
      }
    } else if (other instanceof Projectile) {
      Projectile p = (Projectile) other;
      if (this.getBounds().intersects(p.getX(), p.getY(), p.getImg().getWidth(),
          p.getImg().getHeight())) {
        return true;
      }
    }
    return false;

  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  /**
   * Horizontal movement by adding a speed to the players x position.
   */
  public void moveHorizontally() {
    this.x = x + speedX;
  }

  /**
   * Vertical movement by adding a speed to the players y position.
   */
  public void moveVertically() {
    this.y = y + speedY;
  }

  public double getSpeedX() {
    return speedX;
  }

  public void setSpeedX(double speedX) {
    this.speedX = speedX;
  }

  /**
   * Increase the horizontal speed by adding up a parameter.
   * 
   * @param speedDX
   *          amount to increase the speed with
   */
  public void incrSpeedX(double speedDX) {
    this.speedX = speedX + speedDX;
  }

  public double getSpeedY() {
    return speedY;
  }

  public void setSpeedY(double speedY) {
    this.speedY = speedY;
  }

  /**
   * Increase the vertical speed by adding up a parameter.
   * 
   * @param speedDY
   *          amount to increase the speed with
   */
  public void incrSpeedY(double speedDY) {
    this.speedY = speedY + speedDY;
  }

  @Override
  public Circle getBounds() {
    return new Circle(x + size / 2, y + size / 2, size / 2);
  }

  /**
   * Returns a bounce speed which determines how how the ball bounces depending in the size of the
   * ball.
   * 
   * @return bounce speed
   */
  public int getBounceSpeed() {
    if ((size & (size - 1)) != 0 || size <= 0) {
      throw new IllegalArgumentException();
    }
    return (int) -((Math.log(size) / Math.log(2)) * 2);
  }

  public int getsplitBounce() {
    return splitBounce;
  }

  public void setsplitBounce(int splitBounce) {
    this.splitBounce = splitBounce;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Ball other = (Ball) obj;
    if (size == other.size && speedX == other.speedX && speedY == other.speedY && x == other.x
        && y == other.y) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "Ball{"
            + "x=" + (int) x
            + ", y=" + (int) y
            + ", size=" + size + '}';
  }
}
