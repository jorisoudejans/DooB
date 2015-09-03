package doob.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import doob.model.Ball;
import doob.model.Projectile;
import doob.model.Spike;
import doob.view.PlayerView;

/**
 * Controller for games.
 */
public class GameController {
	
	@FXML
	private Pane lives1;
	@FXML
	private Pane lives2;
	@FXML
	private Label score1;
	@FXML
	private Label score2;
	
	@FXML
	private Canvas canvas;
	private GraphicsContext gc;

	private PlayerController player;

	private GameState gameState;
	private LevelController level;
	
	private ArrayList<Ball> balls;
	private ArrayList<Projectile> projectiles;
	private int ballSpeed = 3;
	private int shootSpeed = 12;
	private int startHeight = 200;
	private int ballSize = 100;
	

    /**
     * Initialization of the game pane.
     */
	@FXML
	public void initialize() {
		projectiles = new ArrayList<Projectile>();
		gameState = GameState.RUNNING;
		level = new LevelController();
        player = new PlayerController(canvas);
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent key) {
                switch (key.getCode()) {
                    case RIGHT:
                        player.moveRight();
                        break;
                    case LEFT:
                        player.moveLeft();
                        break;
                    case SPACE:
                    	//TODO
                    	shoot();
                    	break;
                    default:
                    	player.stand();
                        break;
                }
			}

		});
		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				player.stand();
			}
		});
		canvas.requestFocus();
		balls = new ArrayList<Ball>();
		balls.add(new Ball(0, startHeight, ballSpeed, 0, ballSize));
		gc = canvas.getGraphicsContext2D();
		startTimer();
	}

    public void shoot() {
    	projectiles.add(new Spike(player.getView().getX(), canvas.getHeight(), shootSpeed));
    	//shootProjectiles();
    }
	
	public void moveBalls() {
		for(Ball b : balls) {
			b.moveHorizontally();
			if(b.getX() + b.getSize() >= canvas.getWidth()) b.setSpeedX(-ballSpeed);
			else if(b.getX() <= 0) b.setSpeedX(ballSpeed);
			b.moveVertically();;
			b.incrSpeedY(0.5);
			if(b.getY() + b.getSize() > canvas.getHeight()) {
				b.setSpeedY(b.getBounceSpeed());
			}
		}
	}
	
	public void shootProjectiles() {
		for (Iterator<Projectile> iter = projectiles.listIterator(); iter.hasNext(); ) {
		    Projectile p = iter.next();
		    if (p.getY() <= 0) {
		        iter.remove();
		    } else {
		    	p.shoot();
		    }
		}
	}

    /**
     * Paint all views.
     */
	public void paint() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		player.invalidate(gc);
		for (Projectile b : projectiles) {
			gc.drawImage(b.getImg(), b.getX(), b.getY());
		}
		//player.draw(gc);
		for(Ball b : balls) {
			b.draw(gc);
		}
	}

	/**
	 * Loops through every object in the game to detect collisions.
	 */
	public void detectCollisions() {
		for (Ball b : balls) {
			PlayerView view = player.getView();
			if (b.getBounds().intersects(view.getX(), view.getY(), 
					view.getWidth(), view.getHeight())) {
				//TODO
				System.out.println("Crushed");
				gameState = GameState.LOST;
			}
			for (Iterator<Projectile> iter2 = projectiles.listIterator(); iter2.hasNext(); ) {
				Projectile p = iter2.next();
				if(b.getBounds().intersects(p.getX(), p.getY(), p.getImg().getWidth(), p.getImg().getHeight())) {
					projectiles.remove(p);
					if(b.getSize() >= 15) {
						balls.add(new Ball(b.getX(), b.getY(), ballSpeed, -5, b.getSize() / 2));
						balls.add(new Ball(b.getX(), b.getY(), -ballSpeed, -5, b.getSize() / 2));
					}
					balls.remove(b);
					System.out.println("HIT");
				}
			}
		}

	}

    /**
     * Timer for animation.
     */
	public void startTimer() {
		 new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	               moveBalls();
	               shootProjectiles();
	               detectCollisions();
	               paint();
	               //collide();
	            }
	        }.start();
	}

    /**
     * States the game can be in.
     */
	public enum GameState {
		PAUSED, RUNNING, WON, LOST
	}
}
