package doob.controller;

import doob.model.Ball;
import doob.model.PlayerModel;
import doob.view.PlayerView;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created on 03/09/15 by Joris.
 */
public class PlayerController implements Collidable {

    private PlayerModel mPlayer;
    private PlayerView mView;

    private int speed;

    /**
     * Constructor for player controller.
     * @param initialX initial location of the view.
     */
    public PlayerController(int initialX) {
        this.mPlayer = new PlayerModel();
        //noinspections CheckStyle-IDEA
        this.mView = new PlayerView(initialX, 100, 200);

        this.speed = 0;
    }

    /**
     * Whether a player collides with a ball.
     * @param other the other object.
     * @return whether it collides.
     */
    public boolean collides(Collidable other) {
        if (other instanceof Ball) {
            // a player only collides with a ball
            Ball ball = (Ball) other;
            double distanceX = Math.abs(mView.getX() - ball.getX());
            double distanceY = Math.abs(mView.getY() - ball.getY());
            double py = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

            return py < (mView.getHeight() + ball.getSize());
        }
        return false;
    }

    public void moveRight() {
        mView.moveRight();
    }

    public void moveLeft() {
        mView.moveLeft();
    }

    public void stand() {
        mView.stand();
    }

    public void invalidate(GraphicsContext g) {
        mView.draw(g);
    }

    public int getSpeed() {
        return speed;
    }
}