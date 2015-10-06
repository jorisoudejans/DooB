package doob.level;

import doob.model.Ball;
import doob.model.Level;
import doob.model.Player;
import doob.model.Wall;
import doob.model.Projectile;
import doob.model.Collidable;
import doob.model.powerup.PowerUp;

/**
 * Handles collisions.
 *
 * Created by hidde on 10/6/15.
 */
public class CollisionManager {

    private Level level;
    private CollisionResolver collisionResolver;

    /**
     * Constructs a manager for current level.
     * @param level current level
     */
    public CollisionManager(Level level, CollisionResolver collisionResolver) {
        this.level = level;
        this.collisionResolver = collisionResolver;
    }

    /**
     * Detects collision in the level.
     */
    public void detectCollisions() {
        for (Player player : level.getPlayers()) {
            detectCollision(player);
        }
        for (Ball ball : level.getBalls()) {
            detectCollision(ball);
        }
        for (Projectile p : level.getProjectiles()) {
            if (p.getY() <= 0) {
                collisionResolver.projectileVersusCeiling(p);
            }
        }
    }

    /**
     * Detects collisions for the current player.
     * @param player current
     */
    private void detectCollision(Player player) {
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

    /**
     * Detects collisions for the current ball.
     * @param ball current ball to be checked for collisions
     */
    private void detectCollision(Ball ball) {
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

    private boolean collides(Collidable c1, Collidable c2) {
        return c1.getBounds().getBoundsInParent().intersects(c2.getBounds().getBoundsInParent());
    }
}
