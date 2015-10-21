package doob.level;

import doob.model.Ball;
import doob.model.Collidable;
import doob.model.Level;
import doob.model.Player;
import doob.model.Projectile;
import doob.model.Wall;
import doob.model.powerup.PowerUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles collisions.
 *
 * Created by hidde on 10/6/15.
 */
public class CollisionManager {

    private Level level;
    private CollisionResolver collisionResolver;

    private List<CollisionCallback> callbacks;

    /**
     * Constructs a manager for current level.
     * @param level current level
     * @param collisionResolver the collisionresolver
     */
    public CollisionManager(Level level, CollisionResolver collisionResolver) {
        this.level = level;
        this.collisionResolver = collisionResolver;
    }

    /**
     * Detects collision in the level.
     */
    public void detectCollisions() {
        this.callbacks = new ArrayList<CollisionCallback>();
        for (Player player : level.getPlayers()) {
        	if (player.isAlive()) {
	            detectCollision(player);
	            for (final Projectile p : player.getProjectiles()) {
	                if (p.getY() <= 0) {
	                    callbacks.add(new CollisionCallback() {
	                        @Override
	                        public void perform() {
	                            collisionResolver.projectileVersusCeiling(p);
	                        }
	                    });
	                }
	            }
        	}
        }
        for (Ball ball : level.getBalls()) {
            detectCollision(ball);
        }
        for (CollisionCallback callback : callbacks) {
            callback.perform();
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
        for (Ball ball : level.getBalls()) {
            if (collides(player, ball)) {
                collisionResolver.playerVersusBall(player, ball);
            }
        }
    }

    /**
     * Detects collisions for the current ball.
     * @param ball current ball to be checked for collisions
     */
    private void detectCollision(final Ball ball) {
        for (final Wall w : level.getWalls()) { // ballVersusWall
            if (collides(ball, w)) {
                callbacks.add(new CollisionCallback() {
                    @Override
                    public void perform() {
                        collisionResolver.ballVersusWall(ball, w);
                    }
                });
            }
        }
        for (Player player : level.getPlayers()) {
        	if (player.isAlive()) {
		        for (final Projectile p : player.getProjectiles()) {
		            if (collides(ball, p)) {
		                callbacks.add(new CollisionCallback() {
		                    @Override
		                    public void perform() {
		                        collisionResolver.ballVersusProjectile(ball, p);
		                    }
		                });
		            }
		        }
        	}
        }
    }

    private boolean collides(Collidable c1, Collidable c2) {
        return c1.getBounds().getBoundsInParent().intersects(c2.getBounds().getBoundsInParent());
    }

	/**
     * Interface to provide a callback.
     */
    private interface CollisionCallback {
        /**
         * Perform the action.
         */
        void perform();
    }
}
