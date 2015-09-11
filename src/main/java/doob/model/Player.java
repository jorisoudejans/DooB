package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Player class, acts as both model and view.
 */
public class Player implements Collidable, Drawable {

    private int width;
    private int height;
    private int x;
    private int y;
    private int speed;
    private int lives;

    private Image imageStand;
    private Image imageLeft;
    private Image imageRight;

    /**
     * Constructor for a player with initial location x.
     * @param x initial x location.
     * @param y initial y location.
     * @param width view width.
     * @param height view height.
     */
    public Player(int x, int y, int width, int height, Image imageS, Image imageL, Image imageR) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.speed = 0;
        this.lives = 5;

        imageStand = imageS;
        imageLeft = imageL;
        imageRight = imageR;

    }

    /**
     * Whether a player collides with another object.
     * @param other the other object.
     * @return whether it collides.
     */
    public boolean collides(Collidable other) {
        if (other instanceof Ball) {
            // a player only collides with a ball
            Ball b = (Ball) other;
            double distanceX = Math.abs(x - b.getX());
            double distanceY = Math.abs(y - b.getY());
            double py = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

            return b.getBounds().intersects(x, y, width, height);
        }
        return false;
    }

    public void draw(GraphicsContext g) {
        if (g == null)
            throw new IllegalStateException("GraphicsContext can not be null");
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
}
