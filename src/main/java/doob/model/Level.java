package doob.model;

import doob.DLog;
import doob.level.CollisionManager;
import doob.level.LevelObserver;
import doob.level.PowerUpManager;
import doob.model.powerup.PowerUp;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Level class, created from LevelFactory.
 */
public class Level {


    private Canvas canvas;
    private GraphicsContext gc;

    private ArrayList<Ball> balls;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Player> players;
    private ArrayList<Wall> walls;
    private double currentTime;
    private int time;
    public static final int PLAYER_SPEED = 3;
    public static final int PROJECTILE_START_SPEED = 12;
    public static final long FREEZE_TIME = 2000;
    public static final int PROJECTILE_WIDTH = 7;

    private AnimationTimer timer;

    private static int projectileSpeed = PROJECTILE_START_SPEED;

    private Wall right;
    private Wall left;
    private Wall ceiling;
    private Wall floor;

    private boolean ballFreeze;
    private boolean projectileFreeze;

    private PowerUpManager powerUpManager;
    private CollisionManager collisionManager;

    private List<LevelObserver> observers;

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
     * Initialize javaFx.
     *
     * @param canvas
     *          the canvas to be drawn upon.
     */
    public Level(Canvas canvas) {
        this.canvas = canvas;
        createTimer();
        ballFreeze = false;
        projectileFreeze = false;
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(new KeyPressHandler());

        observers = new ArrayList<LevelObserver>();

        canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                if (key.getCode() != KeyCode.SPACE) {
                    players.get(0).setSpeed(0);
                }
            }
        });

        canvas.requestFocus();
        projectiles = new ArrayList<Projectile>();
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
        if (projectiles.size() < 1) {
            projectiles.add(new Spike(player, player.getX() + player.getWidth() / 2 
            		 - PROJECTILE_WIDTH, canvas.getHeight(), PROJECTILE_START_SPEED));
            DLog.info("Player shot projectile.", DLog.Type.PLAYER_INTERACTION);
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
     * Handle the movement of balls.
     */
    public void moveBalls() {
        if (ballFreeze) {
            return;
        }
        for (Ball b : balls) {
            b.move();
        }
    }

    /**
     * Handle the movements of walls.
     */
    public void animateWalls() {
        for (Wall w : walls) {
            if (w.isMoveable()) {
                w.move();
            }
        }
    }

    /**
     * Paint all views.
     */
    public void paint() {
        // Clear canvas.
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        players.get(0).draw(gc);
        for (Projectile p : projectiles) {
            gc.drawImage(p.getImg(), p.getX(), p.getY());
            p.draw(gc);
        }
        for (Ball b : balls) {
            b.draw(gc);
        }
        for (Wall w : walls) {
            w.draw(gc);
        }
        powerUpManager.onDraw(gc);

        switch (lastEvent) {
            case LOST_LIFE:
                drawText(new Image("/image/crushed.png"));
                break;
            case ZERO_LIVES:
                drawText(new Image("/image/gameover.png"));
                break;
            case ALL_BALLS_GONE:
                drawText(new Image("/image/levelcomplete.png"));
                break;
            default:
                break;
        }
    }

    /**
     * Timer for animation.
     */
    public void update() {
        for (Projectile projectile : projectiles) {
            if (!(projectile.getState() == Projectile.State.FROZEN && projectileFreeze)) {
                projectile.move();
            }
            projectile.draw(gc);
        }
        // endlessLevel();
        collisionManager.detectCollisions();
        moveBalls();
        animateWalls();
        paint();
        currentTime -= 1;
        for (Player player : players) {
            player.move();
        }
        powerUpManager.onUpdate(currentTime);
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
     * Draw a textimage on the canvas.
     * @param i image to draw
     */
    public void drawText(Image i) {
        gc.drawImage(i, canvas.getWidth() / 2 - i.getWidth() / 2,
                canvas.getHeight() / 2 - i.getHeight());
    }

    /**
     * Remove the projectile.
     * @param projectile to remove.
     */
    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
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
     * Add an observer to level.
     * @param observer to add.
     */
    public void addObserver(LevelObserver observer) {
        observers.add(observer);
    }
    /**
     * Notify all observers of an event.
     */
    public void notifyObservers() {
        for (LevelObserver observer : observers) {
            observer.onLevelStateChange(lastEvent);
        }
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

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<PowerUp> getCollidablePowerups() {
        return powerUpManager.getCollidables();
    }

    public void setPowerUpManager(PowerUpManager powerUpManager) {
        this.powerUpManager = powerUpManager;
    }

    public void setCollisionManager(CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

    /**
     * Handler for key presses.
     */
    private class KeyPressHandler implements EventHandler<KeyEvent> {
        private KeyCode last = KeyCode.SPACE;
        public void handle(KeyEvent key) {
            switch (key.getCode()) {
                case RIGHT:
                    players.get(0).setSpeed(players.get(0).getMoveSpeed());
                    if (last != KeyCode.RIGHT) {
                        DLog.info("Player direction changed to right.", 
                        		DLog.Type.PLAYER_INTERACTION);
                        last = KeyCode.RIGHT;
                    }
                    break;
                case LEFT:
                    players.get(0).setSpeed(-players.get(0).getMoveSpeed());
                    if (last != KeyCode.LEFT) {
                        DLog.info("Player direction changed to left.", 
                        		DLog.Type.PLAYER_INTERACTION);
                        last = KeyCode.LEFT;
                    }
                    break;
                case SPACE:
                    shoot(players.get(0));
                    break;
                default:
                    players.get(0).setSpeed(0);
                    break;
            }
        }
    }

}
