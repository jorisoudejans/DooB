package doob.model;

import javafx.scene.shape.Shape;

/**
 * Created by hidde on 9/3/15.
 */
public interface Collidable {

    /**
     * Returns JavaFX shape that corresponds to this entity
     * @return javafx shape
     */
    public Shape getBounds();

}
