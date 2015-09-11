package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

public class Ball implements Collidable, Drawable {

	private double x;
	private double y;
	private double speedX;
	private double speedY;
	private int size;
	private int splitBounce;

	private int canvasWidth;
	private int canvasHeight;
	
	/**
	 * Constructor.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param speedX x-axis speed
	 * @param speedY y-axis speed
	 * @param size the size of the ball
	 */
	public Ball(double x, double y, double speedX, double speedY, int size) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.size = size;
		this.splitBounce = -4;
	}

	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.fillOval(this.getX(), this.getY(), this.getSize(), this.getSize());
	}

	/**
	 * Move the ball in a physics simulation.
	 */
	public void move() {
		moveHorizontally();
		/*if (x + size >= canvasWidth) {
			setSpeedX(-ballSpeed);
		} else if (x <= 0) {
			setSpeedX(ballSpeed);
		}*/
		moveVertically();
		incrSpeedY(0.2);
		/*if (y + size > canvasHeight) {
			setSpeedY(getBounceSpeed());
		}*/
	}
	/**
	 * Split function which handles the creation of new balls after one is hit.
	 * @return A list of new balls.
	 */
	public Ball[] split() {
		Ball ball1 = new Ball(this.x, this.y, speedX, splitBounce, this.size / 2);
		Ball ball2 = new Ball(this.x, this.y, -speedX, splitBounce, this.size / 2);
		Ball[] res =  new Ball[2];
		res[0] = ball1;
		res[1] = ball2;
		return res;		
	}

	public boolean collides(Collidable other) {
        if (other instanceof Wall) {
            Wall w = (Wall) other;
            if (this.getBounds().intersects(w.getX(), w.getY(), w.getWidth(), w.getHeight())) {
                //System.out.println("Hit a wall");
                return true;
            }
        }
        else if (other instanceof Projectile) {
            Projectile p = (Projectile) other;
            if (this.getBounds().intersects(p.getX(), p.getY(), p.getImg().getWidth(), p.getImg().getHeight())) {
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
	
	public void moveHorizontally() {
		this.x = x + speedX;
	}
	
	public void moveVertically() {
		this.y = y + speedY;
	}
	
	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	
	public void incrSpeedX(double speedDX) {
		this.speedX = speedX + speedDX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	
	public void incrSpeedY(double speedDY) {
		this.speedY = speedY + speedDY;
	}

	public Circle getBounds() {
		return new Circle(x + size / 2, y + size / 2, size / 2);
	}
	
	public int getBounceSpeed() {
	  if((size & (size - 1)) != 0 || size <= 0) {
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
    if (size == other.size && speedX == other.speedX && speedY == other.speedY && x == other.x && y == other.y){
      return true;
    }
    return false;
  }
	
}
