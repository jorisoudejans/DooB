package doob.model;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created on 03/09/15 by Joris.
 */
public interface Drawable {

    /**
     * Method to draw the drawable on the canvas.
     * @param gc context to draw in.
     */
    void draw(GraphicsContext gc);

}
