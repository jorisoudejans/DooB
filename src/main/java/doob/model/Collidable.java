package doob.model;

import javafx.scene.shape.Shape;

/**
 * Interface to be implemented by all collidable objects.
 */
public interface Collidable {

    /**
     * Returns JavaFX shape that corresponds to this entity.
     * @return javafx shape
     */
    Shape getBounds();

}
