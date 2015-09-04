package doob.model.projectile;

import doob.model.Collidable;
import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract class for any projectile to be fired from the Player.
 */
public abstract class AbstractProjectile implements Collidable {

    private int speed;

    /**
     * Default constructor for projectile.
     * @param speed speed at which the projectile moves upwards.
     */
    public AbstractProjectile(int speed) {
        this.speed = speed;
    }

    /**
     * Fire the projectile, straight up, from x.
     * @param g GraphicsContext to draw projectile on.
     * @param x location from where to fire projectile from.
     */
    public abstract void fire(GraphicsContext g, int x);

    /**
     * Get the speed of the projectile.
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set the speed of the projectile.
     * @param speed speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
