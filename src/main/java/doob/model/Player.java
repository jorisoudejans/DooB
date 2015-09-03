package doob.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by hidde on 9/3/15.
 */
public class Player extends Sprite implements Collidable {

    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;

    private Direction direction;

    private Image imageStand;
    private Image imageLeft;
    private Image imageRight;
    private Image playerShoot;

    public Player(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = 20;

        imageStand = new Image("/image/character1_stand.png");
        imageLeft = new Image("/image/character1_left.gif");
        imageRight = new Image("/image/character1_right.gif");
        //playerShoot = new Image("/image/characte")

        this.direction = Direction.STAND;
    }

    public void draw(GraphicsContext g) {
        switch (direction) {
            case STAND:
                g.drawImage(imageStand, x, y);
                break;
            case LEFT:
                g.drawImage(imageLeft, x, y);
                break;
            case RIGHT:
                g.drawImage(imageRight, x, y);
                break;
        }
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void move() {
        this.x = x + speed;
    }

    /**
     * Whether a player collides with a ball
     * @param other the other object
     * @return whether it collides
     */
    public boolean collides(Collidable other) {
        if (other instanceof Ball) {
            // a player only collides with a ball
            Ball ball = (Ball)other;
            double distanceX = Math.abs(x - ball.getX());
            double distanceY = Math.abs(y - ball.getY());
            double py = Math.sqrt( Math.pow(distanceX, 2) + Math.pow(distanceY, 2) );

            return py < ( getHeight() + ball.getSize() );
        }
        return false;
    }

    public enum Direction {
        STAND, LEFT, RIGHT
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Moves the character to the right.
     */
    public void moveRight() {
        this.x = x + speed;
        this.direction = Direction.RIGHT;
    }

    /**
     * Moves the character to the left.
     */
    public void moveLeft() {
        this.x = x - speed;
        this.direction = Direction.LEFT;
    }

    /**
     * The character stands in idle.
     */
    public void stand() {
        this.direction = Direction.STAND;
    }


}
