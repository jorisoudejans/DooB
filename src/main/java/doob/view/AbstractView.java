package doob.view;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created on 03/09/15 by Joris.
 */
public abstract class AbstractView {

    /**
     * Draw the view on the canvas.
     * @param g canvas holder.
     */
    public abstract void draw(GraphicsContext g);

}
