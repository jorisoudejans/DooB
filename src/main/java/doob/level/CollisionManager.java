package doob.level;

import doob.model.*;
import doob.model.powerup.PowerUp;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Handles collisions.
 *
 * Created by hidde on 10/6/15.
 */
public class CollisionManager {

    private Level level;
    private CollisionResolver collisionResolver;

    public CollisionManager(Level level) {
        this.level = level;
        this.collisionResolver = new CollisionResolver(level);
    }

    /**
     * Detects collision in the level
     */
    public void detectCollisions() {
        for (Player player : level.getPlayers()) {
            for (Wall w : level.getWalls()) { // playerVersusWall
                if (collides(player, w)) {
                    collisionResolver.playerVersusWall(player, w);
                }
            }
            for (PowerUp powerUp : level.getCollidablePowerups()) {
                if (collides(player, powerUp)) {
                    collisionResolver.playerVersusPowerUp(player, powerUp);
                }
            }
        }
        for (Ball ball : level.getBalls()) {
            for (Wall w : level.getWalls()) { // ballVersusWall
                if (collides(ball, w)) {
                    collisionResolver.ballVersusWall(ball, w);
                }
            }
            for (Projectile p : level.getProjectiles()) {
                if (collides(ball, p)) {
                    collisionResolver.ballVersusProjectile(ball, p);
                }
            }
        }
        for (Projectile p : level.getProjectiles()) {
            if (p.getY() <= 0) {
                collisionResolver.projectileVersusCeiling(p);
            }
        }
    }

    private boolean collides(Collidable c1, Collidable c2) {
        return c1.getBounds().getBoundsInParent().intersects(c2.getBounds().getBoundsInParent());
    }
}
