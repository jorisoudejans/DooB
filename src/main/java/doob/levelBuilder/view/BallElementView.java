package doob.levelBuilder.view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import doob.levelBuilder.BallElement;
import doob.model.Ball;

/**
 * The view of the ball element class.
 *
 */
public class BallElementView extends BuilderElementView implements Observer {
	
	private BallElement be;
	private Color color;
	
	/**
	 * Constructor.
	 * @param be BallElement that it observes.
	 * @param gc GraphicsContext that can draw to the screen.
	 */
	public BallElementView(BallElement be, GraphicsContext gc) {
		super(be, gc);
		this.be = be;
		this.color = Ball.getColor(be.getSize());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		draw();
	}
	
	@Override
	public void draw() {
		gc.setFill(color);
		gc.fillOval(be.getX(), be.getY(), be.getSize(), be.getSize());
		gc.setFill(Color.BLACK);
	}

}
