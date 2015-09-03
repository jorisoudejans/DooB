package doob.model;

/**
 * Created by hidde on 9/3/15.
 */
public interface Collidable {

    /**
     * Tells whether the collidable object is colliding with another
     * @param other the other object
     * @return whether it collides
     */
    public boolean collides(Collidable other);

}
