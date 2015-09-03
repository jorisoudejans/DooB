package doob.model.projectile;

import doob.model.Collidable;
import doob.model.Sprite;
import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract class for any projectile to be fired from the Player.
 */
public abstract class AbstractProjectile implements Collidable {

    private int speed;
    private Sprite pointSprite;
    private Sprite tailSprite;

    /**
     * Default constructor for projectile.
     * @param speed speed at which the projectile moves upwards.
     * @param pointSprite Sprite used for the head of the projectile.
     * @param tailSprite Sprite used for the tail of the projectile.
     */
    public AbstractProjectile(int speed, Sprite pointSprite, Sprite tailSprite) {
        this.speed = speed;
        this.pointSprite = pointSprite;
        this.tailSprite = tailSprite;
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

    /**
     * Get the pointSprite of the projectile.
     * @return Sprite used for the head of the projectile.
     */
    public Sprite getPointSprite() {
        return pointSprite;
    }

    /**
     * Set the pointSprite of the projectile.
     * @param pointSprite Sprite used for the head of the projectile.
     */
    public void setPointSprite(Sprite pointSprite) {
        this.pointSprite = pointSprite;
    }

    /**
     * Get the tailSprite of the projectile.
     * @return Sprite used for the tail of the projectile.
     */
    public Sprite getTailSprite() {
        return tailSprite;
    }

    /**
     * Set the tailSprite of the projectile.
     * @param tailSprite Sprite used for the tail of the projectile.
     */
    public void setTailSprite(Sprite tailSprite) {
        this.tailSprite = tailSprite;
    }
}
