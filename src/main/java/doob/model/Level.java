package doob.model;

import doob.DLog;
import doob.level.CollisionManager;
import doob.level.CollisionResolver;
import doob.level.PowerUpManager;
import doob.model.powerup.PowerUp;
import doob.util.BoundsTuple;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Level class, created from LevelFactory.
 */
public abstract class Level extends Observable {

    private DLog dLog;

    private List<Ball> balls;
    private List<Player> players;
    private List<Wall> walls;
    private double currentTime;
    private int time;
    public static final int PROJECTILE_START_SPEED = 12;
    public static final long FREEZE_TIME = 2000;
    public static final int PROJECTILE_WIDTH = 7;

    private AnimationTimer timer;

    private static int projectileSpeed = PROJECTILE_START_SPEED;

    private Wall right;
    private Wall left;
    private Wall ceiling;
    private Wall floor;

    private BoundsTuple bounds;

    private boolean ballFreeze;
    private boolean projectileFreeze;

    private PowerUpManager powerUpManager;
    private CollisionManager collisionManager;

    private Event lastEvent = Event.NULL;

    /**
     * States the Level can have.
     */
    public enum Event {
        NULL,
        ZERO_LIVES,
        LOST_LIFE,
        ALL_BALLS_GONE,
    }

    /**
     * Init new level with size bounds.
     * @param bounds width and height
     */
    public Level(BoundsTuple bounds) {
        dLog = DLog.getInstance();
        this.bounds = bounds;
        createTimer();
        ballFreeze = false;
        projectileFreeze = false;

        powerUpManager = new PowerUpManager(this);
        collisionManager = new CollisionManager(this, new CollisionResolver(this));
    }

    /**
     * Create animation timer.
     */
    private void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    /**
     * Create a new projectile and move it.
     *
     * @param player
     *          the player that shoots the projectile.
     */
    public void shoot(Player player) {
        if (player.getProjectiles().size() < 1) {
        	player.getProjectiles().add(new Spike(player, player.getX() + player.getWidth() / 2 
            		 - PROJECTILE_WIDTH, bounds.getHeight(), PROJECTILE_START_SPEED));
            dLog.info(player.toString() + " shot projectile.", DLog.Type.PLAYER_INTERACTION);
        }
    }

    /**
     * Create a freeze of 2 seconds when a level fails or is completed.
     * @param afterFreeze handler for what to do after the freeze.
     */
    public void freeze(EventHandler<WorkerStateEvent> afterFreeze) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(FREEZE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        timer.stop();
        sleeper.setOnSucceeded(afterFreeze);
        new Thread(sleeper).start();
    }

    /**
     * Timer for animation.
     */
    public void update() {
        collisionManager.detectCollisions();
        powerUpManager.onUpdate(currentTime);
        setChanged();
        notifyObservers(this.lastEvent);
        currentTime += getTimeMutation();
    }

    /**
     * Time mutation for the cycle in this level.
     * @return int the time should be mutated with
     */
    abstract int getTimeMutation();

    /**
     * Stop timer.
     */
    public void stopTimer() {
        timer.stop();
    }
    
    /**
     * Start timer.
     */
    public void startTimer() {
        timer.start();
    }

    /**
     * Remove the projectile.
     * @param projectile to remove.
     */
    public void removeProjectile(Projectile projectile) {
    	Player p = projectile.getPlayer();
    	p.getProjectiles().remove(projectile);
    }

    /**
     * Remove a ball.
     * @param ball to remove.
     */
    public void removeBall(Ball ball) {
        balls.remove(ball);
    }
    
    /**
     * Add a ball to the level.
     * @param ball to add
     */
    public void addBall(Ball ball) {
        balls.add(ball);
    }
    
    /**
     * Function to record the last event.
     * @param lastEvent the event
     */
    public void onEvent(Event lastEvent) {
        this.lastEvent = lastEvent;
    }

    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }

    public Wall getRight() {
        return right;
    }
    
    public void setRight(Wall right) {
        this.right = right;
    }

    public Wall getLeft() {
        return left;
    }

    public void setLeft(Wall left) {
        this.left = left;
    }

    public Wall getCeiling() {
        return ceiling;
    }

    public void setCeiling(Wall ceiling) {
        this.ceiling = ceiling;
    }


    public Wall getFloor() {
        return floor;
    }

    public void setFloor(Wall floor) {
        this.floor = floor;
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public static int getProjectileSpeed() {
        return projectileSpeed;
    }

    public static void setProjectileSpeed(int projectileSpeed) {
        Level.projectileSpeed = projectileSpeed;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public boolean isBallFreeze() {
        return ballFreeze;
    }

    public void setBallFreeze(boolean ballFreeze) {
        this.ballFreeze = ballFreeze;
    }

    public boolean isProjectileFreeze() {
        return projectileFreeze;
    }

    public void setProjectileFreeze(boolean projectileFreeze) {
        this.projectileFreeze = projectileFreeze;
    }

    public ArrayList<PowerUp> getCollidablePowerups() {
        return powerUpManager.getCollidables();
    }

	/**
     * Continues to next level immediately after event.
     */
    public void continueNextLevel() {
        setChanged();
        notifyObservers(this.lastEvent);
    }

}
