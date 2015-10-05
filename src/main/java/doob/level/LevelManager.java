package doob.level;

import doob.model.Collidable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Abstract level manager for all external functionality for level
 *
 * Created by hidde on 10/5/15.
 */
public interface LevelManager {

    /**
     * Level update, or "game tick" if you will
     */
    public void onUpdate(double time);

    /**
     * Draw callback.
     * @param gc current graphics context
     */
    public void onDraw(GraphicsContext gc);

    /**
     * When the level inits
     */
    public void onInit();

    /**
     * Callback to determine whether the object should collide with the things this manager manages
     * @param collider active
     * @return true if they collide
     */
    public boolean itemsCanCollideWith(Collidable collider);

    public void handleCollision(Collidable collider, Collidable collidee);

    /**
     * Returns collidable items of this manager
     * @return items
     */
    public ArrayList<? extends Collidable> getCollidables();

    /**
     * Callback when a collision occurs
     * @param collider the collider
     * @param collidee the subject
     */
    public void onCollide(Object collider, Object collidee);
}
