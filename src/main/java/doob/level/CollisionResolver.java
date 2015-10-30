package doob.level;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import doob.DLog;
import doob.model.Ball;
import doob.model.Player;
import doob.model.Projectile;
import doob.model.Wall;
import doob.model.level.Level;
import doob.model.powerup.PowerUp;
import doob.util.SoundManager;
import doob.util.TupleTwo;

/**
 * Class to handle collision resolving.
 *
 * Created by hidde on 10/6/15.
 */
public class CollisionResolver {

    private Level level;

    private DLog dLog;

    /**
     * Constructor for CollisionResolver. Takes Level as a parameter to act upon.
     * @param level the resolver is to act upon.
     */
    public CollisionResolver(Level level) {
        this.level = level;
        dLog = DLog.getInstance();
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
        player.die();

        final boolean gameOver = player.getLives() <= 1;
        if (gameOver) {
            level.onEvent(Level.Event.ZERO_LIVES);
        } else {
            level.onEvent(Level.Event.LOST_LIFE);
        }
        
        dLog.info("Lost a life", DLog.Type.STATE);
    }

    /**
     * Method called when a Player and a PowerUp collide.
     * @param player collider
     * @param powerup collider
     */
    public void playerVersusPowerUp(Player player, PowerUp powerup) {
        level.getPowerUpManager().handleCollision(powerup, player);
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
        } else if (wall.isMoveable() || wall.equals(level.getCeiling())) {
            level.removeBall(ball);
            handleDisappearingBall(ball);
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
            dLog.info(ball.toString() + " splits", DLog.Type.COLLISION);
            Ball[] res = ball.split();
            projectile.getPlayer().incrScore(Ball.SCORE);
            level.addBall(res[0]);
            level.addBall(res[1]);
        } else {
            dLog.info(ball.toString() + " disappears", DLog.Type.COLLISION);
        }

        level.removeBall(ball);
        level.removeProjectile(projectile);
        level.getPowerUpManager().spawnPowerups(ball.getX(), ball.getY());

        handleDisappearingBall(ball);
        SoundManager.playSound(SoundManager.POP_EFFECT);
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

    /**
     * Handles the disappearing of a ball. Moves walls or sets game over.
     * @param disappearingBall the ball
     */
    private void handleDisappearingBall(Ball disappearingBall) {
        TupleTwo<Wall> walls = getClosestWalls(disappearingBall);

        if (!isSpaceEmpty(walls)) {
            return;
        }

        if (walls.t0 == level.getLeft() && walls.t1 == level.getRight()) {
            level.onEvent(Level.Event.ALL_BALLS_GONE);
            level.stopTimer();
            level.notifyObservers();
            dLog.info("All balls gone", DLog.Type.STATE);
        } else if (walls.t0 == level.getLeft()) {
            walls.t1.setOpen(true);
        } else if (walls.t1 == level.getLeft()) {
            walls.t0.setOpen(true);
        }

    }

    /**
     * Determines the closest walls to a ball.
     * @param ball the object
     * @return tuple with left and right wall
     */
    private TupleTwo<Wall> getClosestWalls(Ball ball) {
        Wall closestLeft = level.getLeft();
        Wall closestRight = level.getRight();

        for (Wall wall : level.getWalls()) {
            if (!wall.isOpen() && wall.getX() > closestLeft.getX() && wall.getX() < ball.getX()) {
                closestLeft = wall;
            } else if (
                    !wall.isOpen()
                    && wall.getX() < closestRight.getX()
                    && wall.getX() > ball.getX()
                    ) {
                closestRight = wall;
            }
        }

        return new TupleTwo<Wall>(closestLeft, closestRight);
    }

    /**
     * Determines if space between to walls is empty.
     * @param walls tuple
     * @return true if there exist no other balls
     */
    private boolean isSpaceEmpty(TupleTwo<Wall> walls) {
        for (Ball ball : level.getBalls()) {
            if (ball.getX() > walls.t0.getX() && ball.getX() < walls.t1.getX()) {
                return false;
            }
        }
        return true;
    }

}
