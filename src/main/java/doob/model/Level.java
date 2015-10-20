package doob.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Random;

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



/**
 * Level class, created from LevelFactory.
 */
public class Level extends Observable {

    private DLog dLog;

    private ArrayList<Ball> balls;
    private ArrayList<Player> players;
    private ArrayList<Wall> walls;
    private double currentTime;
    private int time;
    public static final int PROJECTILE_START_SPEED = 12;
    public static final long FREEZE_TIME = 2000;
    public static final int PROJECTILE_WIDTH = 7;
    private static final int DIFFICULTY_TIME = 1000;
    private static final int SMALLEST_BALL_SIZE = 16;
    private static final int NUMBER_POSSIBLE_BALLS = 5;

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

    private boolean survival = false;
    private int nextWaveHP = 0;

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
        if (survival) {
            updateSurvival();
        } else {
            currentTime -= 1;
        }
    }

    /**
     * Update level when in survival mode.
     */
    private void updateSurvival() {
        currentTime += 1;
        if (currentTime > DIFFICULTY_TIME) {
            currentTime -= DIFFICULTY_TIME;
            nextWaveHP++;
        }
        if (totalBallHitpoints() <= nextWaveHP) {
            spawnBalls(System.currentTimeMillis());
        }
    }

    /**
     * Spawns a randomly selected set of balls.
     * @param seed the random seed
     */
    public void spawnBalls(long seed) {

        Random generator = new Random(seed);
        int i = Math.abs(generator.nextInt() % 4);

        spawnSameSizeBalls(4 - i, (int) (8 * Math.pow(2, i + 1)));
    }

    /**
     * calculates the amount of time you would need to hit all the
     * balls in order for them to all be gone.
     * @return the sum of hitpoints the balls have
     */
    public int totalBallHitpoints() {
        int sum = 0;
        for (Ball b: balls) {
            sum += ballHitpoints(0, b.getSize());
        }
        return sum;
    }

    /**
     * Calculates the amount of times a ball would
     * need to be hit to be gone.
     * @param sum current sum of hitpoints
     * @param size current size of ball
     * @return total sum of hitpoints
     */
    public static int ballHitpoints(int sum, int size) {
        if (size <= SMALLEST_BALL_SIZE) {
            return sum + 1;
        }
        return 1 + 2 * ballHitpoints(sum, size / 2);
    }

    /**
     * Spawns an amount of balls of the same size equally spaced out.
     * @param amount The amount of balls to spawn
     * @param size The size of the balls
     */
    public void spawnSameSizeBalls(int amount, int size) {
        double y = bounds.getHeight() / 4;
        for (int i = 0; i < amount; i++) {
            int direction;
            direction = (int) (Math.pow(
                    -1,
                    (int) Math.round(Math.random() * 10)) * 2
            );
            double x = bounds.getWidth() / (amount + 1) * (i + 1);
            Ball ball = new Ball(x, y, direction, 0, size);
            this.balls.add(ball);
        }
    }

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

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public void setPlayers(ArrayList<Player> players) {
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
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

    public boolean isSurvival() {
        return survival;
    }

    public void setSurvival(boolean survival) {
        this.survival = survival;
    }

    public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	/**
     * Continues to next level immediately after event.
     */
    public void continueNextLevel() {
        setChanged();
        notifyObservers(this.lastEvent);
    }

    /**
     * Class that assists in building level.
     */
    public static class Builder {

        private BoundsTuple bounds;
        private ArrayList<Ball> balls;
        private ArrayList<Player> players;
        private ArrayList<Wall> walls;
        private int time;

        /**
         * Canvas setter.
         * @param bounds level size
         * @return the Builder-object
         */
        public Builder setBounds(BoundsTuple bounds) {
            this.bounds = bounds;
            return this;
        }

        /**
         * Time setter.
         * @param time time
         * @return the Builder-object
         */
        public Builder setTime(int time) {
            this.time = time;
            return this;
        }

        /**
         * Balls setter.
         * @param balls balls
         * @return the Builder-object
         */
        public Builder setBalls(ArrayList<Ball> balls) {
            this.balls = balls;
            return this;
        }

        /**
         * Walls setter.
         * @param walls walls
         * @return the Builder-object
         */
        public Builder setWalls(ArrayList<Wall> walls) {
            this.walls = walls;
            return this;
        }

        /**
         * Player setter.
         * @param players players
         * @return the Builder-object
         */
        public Builder setPlayers(ArrayList<Player> players) {
            this.players = players;
            return this;
        }

        /**
         * Builds the level.
         * @return level
         */
        public Level build() {
            Level level = new Level(bounds);
            Wall right = new Wall(bounds.getWidth().intValue(), 0, 1, bounds.getHeight().intValue());
            Wall left = new Wall(0, 0, 1, bounds.getHeight().intValue());
            Wall ceiling = new Wall(0, 0, bounds.getWidth().intValue(), 1);
            Wall floor = new Wall(0, bounds.getHeight().intValue(), bounds.getWidth().intValue(), 1);

            level.setLeft(left);
            level.setRight(right);
            level.setCeiling(ceiling);
            level.setFloor(floor);

            Collections.sort(walls, new Comparator<Wall>() {
                @Override
                public int compare(Wall w1, Wall w2) {
                    return Integer.compare(w1.getX(), w2.getX());
                }
            });

            walls.add(right);
            walls.add(0, left);
            walls.add(floor);
            walls.add(ceiling);
            level.setBalls(balls);
            level.setPlayers(players);
            level.setWalls(walls);
            level.setTime(time);
            level.setCurrentTime(time);
            return level;
        }
    }

}
