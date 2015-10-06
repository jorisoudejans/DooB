package doob.level;

import doob.DLog;
import doob.model.*;
import doob.model.powerup.PowerUp;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

/**
 * Class to handle collision resolving.
 *
 * Created by hidde on 10/6/15.
 */
public class CollisionResolver {

    private Level level;

    /**
     * Constructor for CollisionResolver. Takes Level as a parameter to act upon.
     * @param level the resolver is to act upon.
     */
    public CollisionResolver(Level level) {
        this.level = level;
    }

    /**
     * Method called when a Player and a Wall collide.
     * @param player collider
     * @param wall collider
     */
    public void playerVersusWall(Player player, Wall wall) {
        int xSpeed = player.getSpeed();
        if (wall.isOpen()) {
            return;
        }
        if (xSpeed > 0) {
            if (player.getX() + player.getWidth() >= wall.getX()) {
                player.setX(wall.getX()  - player.getWidth() - Player.BOUNCE_BACK_DISTANCE);
            }
        } else if (xSpeed < 0) {
            if (player.getX() <= wall.getX() + wall.getWidth()) {
                player.setX(wall.getX() + wall.getWidth() + Player.BOUNCE_BACK_DISTANCE);
            }
        } else {
            if (player.getX() == wall.getX() + wall.getWidth()) {
                player.setX(player.getX() + 1);
            } else {
                player.setX(player.getX() - 1); }
        }
    }

    /**
     * Method called when a Player and a Ball collide.
     * @param player collider
     * @param ball collider
     */
    public void playerVersusBall(Player player, Ball ball) {
        if (player.getState() == Player.State.INVULNERABLE) {
            return;
        }
        final boolean gameOver = player.getLives() <= 1;
        if (gameOver) {
            level.drawText(new Image("/image/gameover.png"));
        } else {
            level.drawText(new Image("/image/crushed.png"));
        }

        level.freeze(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if (gameOver) {
                    level.notifyObservers(Level.State.ZERO_LIVES);
                } else {
                    level.notifyObservers(Level.State.LOST_LIFE);
                    DLog.info("Lost a life", DLog.Type.STATE);
                    level.crushed();
                    level.startTimer();
                }
            }
        });
    }

    /**
     * Method called when a Player and a PowerUp collide.
     * @param player collider
     * @param wall collider
     */
    public void playerVersusPowerUp(Player player, PowerUp wall) {
        level.getPowerUpManager().onCollide(player, wall);
    }

    /**
     * Method called when a Ball and a Wall collide. Remove ball when it hits a movable
     * ceiling. When it hits te floor, bounce. When it hits a side wall, bounce horizontally.
     * @param ball collider
     * @param wall collider
     */
    public void ballVersusWall(Ball ball, Wall wall) {
        if (wall.equals(level.getFloor())) {
            ball.setSpeedY(ball.getBounceSpeed());
        } else if (wall.isMoveable()) {
            level.removeBall(ball);
        } else {
            ball.setSpeedX(-ball.getSpeedX());
        }
    }

    /**
     * Method called when a Ball and a Projectile collide. Ball disappears when size is
     * smaller than the minimum. If not, the ball splits into two smaller balls.
     * @param ball collider
     * @param projectile collider
     */
    public void ballVersusProjectile(Ball ball, Projectile projectile) {
        if (ball.getSize() >= Ball.MIN_SIZE) {
            DLog.info(ball.toString() + " splits", DLog.Type.COLLISION);
            Ball[] res = ball.split();
            projectile.getPlayer().incrScore(Ball.SCORE);
            level.addBall(res[0]);
            level.addBall(res[1]);
        } else {
            DLog.info(ball.toString() + " disappears", DLog.Type.COLLISION);
        }

        level.removeBall(ball);
        level.removeProjectile(projectile);
        level.getPowerUpManager().onCollide(ball, projectile);

        if (level.getBalls().size() <= 0) {
            level.notifyObservers(Level.State.ALL_BALLS_GONE);
        }
    }

    /**
     * Method called when a projectile has hit the ceiling. If power-up ProjectileFreeze
     * is active, freeze the projectile. Otherwise, remove the projectile.
     * @param projectile subject
     */
    public void projectileVersusCeiling(Projectile projectile) {
        if (level.isProjectileFreeze()) {
            projectile.setState(Projectile.State.FROZEN);
        } else {
            level.removeProjectile(projectile);
        }
    }

}
