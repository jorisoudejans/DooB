package doob.view;

import java.util.ArrayList;
import java.util.Iterator;

import doob.model.PlayerModel;
import doob.model.Projectile;
import doob.model.Spike;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created on 03/09/15 by Joris.
 */
public class PlayerView extends AbstractView {

    private Direction direction;
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int shootSpeed = 12;
    
    private Image imageStand;
    private Image imageLeft;
    private Image imageRight;
    private Image playerShoot;
    
    //TODO see other todo on this page
	//private ArrayList<Projectile> projectiles;

    /**
     * Constructor for a player with initial location x.
     * @param x initial x location.
     * @param y initial y location.
     * @param width view width.
     * @param height view height.
     */
    public PlayerView(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = 0;

        imageStand = new Image("/image/character1_stand.png");
        imageLeft = new Image("/image/character1_left.gif");
        imageRight = new Image("/image/character1_right.gif");
        
        //projectiles = new ArrayList<Projectile>();
        
        direction = Direction.STAND;
    }

    @Override
    public void draw(GraphicsContext g) {
        switch (direction) {
            case STAND:
                g.drawImage(imageStand, x, y);
                break;
            case LEFT:
                g.drawImage(imageLeft, x, y);
                break;
            case RIGHT:
                g.drawImage(imageRight, x, y);
                break;
            default:
                g.drawImage(imageStand, x, y);
                break;
        }
    }

    /**
     * Moves the character to the right.
     */
    public void moveRight() {
        this.x = x + 20;
        this.direction = Direction.RIGHT;
    }

    /**
     * Moves the character to the left.
     */
    public void moveLeft() {
        this.x = x - 20;
        this.direction = Direction.LEFT;
    }
    
    /*
    //TODO These two methods are not yet used here, they are used in gamecontroller 
    //but they should eventually be here (I think)
    public void shoot() {
    	projectiles.add(new Spike(x, height, shootSpeed));
    	shootProjectiles();
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
    */
    /**
     * The character stands in idle.
     */
    public void stand() {
        this.direction = Direction.STAND;
    }

    /**
     * Enumeration used for which direction the player is moving.
     */
    public enum Direction {
        STAND, LEFT, RIGHT
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
