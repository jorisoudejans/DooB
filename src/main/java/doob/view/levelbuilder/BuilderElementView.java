package doob.view.levelbuilder;

import java.util.Observable;

import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract superclass for all view elements that can be drawn to the screen.
 * @author Jasper
 *
 */
public abstract class BuilderElementView {
	
	protected GraphicsContext gc;
	protected Observable ov;

	/**
	 * Constructor.
	 * @param gc The graphicscontext object that can draw to the screen.
	 * @pararm ov The builderelement this view observes
	 */
	public BuilderElementView(Observable ov, GraphicsContext gc) {
		this.gc = gc;
		this.ov = ov;
	}
	
	/**
	 * Draw the element to the screen.
	 */
	public abstract void draw();
	
}
