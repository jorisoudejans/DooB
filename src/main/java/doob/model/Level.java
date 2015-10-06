package doob.model;

import doob.App;
import doob.DLog;
import doob.level.CollisionManager;
import doob.level.LevelObserver;
import doob.level.CollisionResolver;
import doob.level.LevelManager;
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
import java.util.Collections;
import java.util.Comparator;
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
    private int playerSpeed = PLAYERSPEED;
    public static final int PLAYERSPEED = 3;
    public static final int PROJECTILE_START_SPEED = 12;
    public static final long FREEZE_TIME = 2000;

    private AnimationTimer timer;

    private static int projectileSpeed = PROJECTILE_START_SPEED;

    private Wall right;
    private Wall left;
    private Wall ceiling;
    private Wall floor;

    private boolean endlessLevel;
    private int flag = 0;
    private boolean ballFreeze;
    private boolean projectileFreeze;

    private PowerUpManager powerUpManager;
    private CollisionManager collisionManager;

    private List<LevelObserver> observers;

    /**
     * States the Level can have.
     */
    public enum State {
        ZERO_LIVES,
        LOST_LIFE,
        NO_TIME_LEFT,
        ALL_BALLS_GONE,
    }

    /**
     * Initialize javaFx.
     *
     * @param canvas
     *          the canvas to be drawn upon.
     */
    public Level(Canvas canvas) {
        //this.checkedWalls = new ArrayList<Wall>();
        this.endlessLevel = true;
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
            projectiles.add(new Spike(player, player.getX() + player.getWidth() / 2, canvas.getHeight(),
                    PROJECTILE_START_SPEED));
            DLog.info("Player shot projectile.", DLog.Type.PLAYER_INTERACTION);
        }
    }

    /**
     * Function that checks a collision between a projectile and a ceiling.
     */
    public void projectileCeilingCollision() {
        int projHitIndex = -1;
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if (p.getY() <= 0) {
                projHitIndex = i;
            } else {
                for (Wall w : walls) {
                    if (w.collides(p)) {
                        projHitIndex = i;
                    }
                }
            }
        }
        if (projHitIndex != -1) {
            if (projectileFreeze) {
                projectiles.get(projHitIndex).setState(Projectile.State.FROZEN);
            } else {
                projectiles.remove(projHitIndex);
            }
        }
    }

    /**
     * Checks for each ball if it collides with a projectile.
     */
    public void ballProjectileCollision() {
        int ballHitIndex = -1;
        int projHitIndex = -1;
        Ball[] res = null;
        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            for (int j = 0; j < projectiles.size(); j++) {
                Projectile p = projectiles.get(j);
                if (p.collides(b)) {
                    projHitIndex = j;
                    if (b.getSize() >= 32) {
                        DLog.info(b.toString() + " splits", DLog.Type.COLLISION);
                        res = b.split();
                    } else {
                        DLog.info(b.toString() + " disappears", DLog.Type.COLLISION);
                    }
                    ballHitIndex = i;
                    players.get(0).incrScore(100);

                    // Temporarily
                    powerUpManager.onCollide(p, b);
                }
            }
        }
        if (ballHitIndex != -1) {
            balls.remove(ballHitIndex);
        }
        if (projHitIndex != -1) {
            projectiles.remove(projHitIndex);
        }
        if (res != null) {
            balls.add(res[0]);
            balls.add(res[1]);
        }
    }

    /**
     * Function which checks if balls collide with walls.
     */
    public void ballWallCollision() {
        int ballHitIndex = -1;
        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            if (b.collides(floor)) {
                b.setSpeedY(b.getBounceSpeed());
            }
            for (Wall w : walls) {
                if (b.collides(w)) {
                    if (w.isMoveable()) {
                        ballHitIndex = i;
                        // TODO add points
                    } else {
                        double speedX = b.getSpeedX();
                        b.setSpeedX(-1 * speedX);
                    }
                }
            }
            /*for (Wall w : checkedWalls) {
                if (b.collides(w)) {
                    if (w.isMoveable()) {
                        ballHitIndex = i;
                        // TODO add points
                    } else {
                        double speedX = b.getSpeedX();
                        b.setSpeedX(-1 * speedX);
                    }
                }
            }*/
            if (ballHitIndex != -1) {
                balls.remove(ballHitIndex);
                ballHitIndex = -1;
            }
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
        sleeper.setOnSucceeded(afterFreeze);
        new Thread(sleeper).start();
        timer.stop();
    }

    /**
     * Function that detects collisions between players and walls.
     */
    public void playerWallCollision() {
        for (Player p : players) {
            if (p.getX() <= left.getX()) {
                // Player wants to pass the left wall
                p.setX(0);
            } else if (p.getX() + p.getWidth() >= right.getX()) {
                // Player wants to pass the right wall
                p.setX((int) canvas.getWidth() - p.getWidth());
            }
            playerWallHelper(walls, p);
            //playerWallHelper(checkedWalls, p);
        }
    }

    /**
     * Helper function for playerwallcollision.
     * @param walls to check
     * @param player the player
     */
    public void playerWallHelper(ArrayList<Wall> walls, Player player) {
        for (Wall wall : walls) {
            if (player.collides(wall)) {
                int xSpeed = player.getSpeed();
                if (xSpeed > 0) {
                    if (player.getX() + player.getWidth() >= wall.getX()) {
                        player.setX(wall.getX() - 10 - player.getWidth());
                    }
                } else if (xSpeed < 0) {
                    if (player.getX() <= wall.getX() + wall.getWidth()) {
                        player.setX(wall.getX() + wall.getWidth() + 10);
                    }
                } else {
                    if (player.getX() == wall.getX() + wall.getWidth()) {
                        player.setX(player.getX() + 1);
                    } else {
                        player.setX(player.getX() - 1); }
                }
            }
        }
    }

    /**
     * Function that checks if all balls in a compartment are gone.
     * Compartment should open.
     */
    public void ballWallCheck() {
        if (walls.size() > 2) {
            for (int i = 0; i < walls.size() - 1; i++) {
                //first wall is the left wall
                Wall left = walls.get(i);
                Wall right = walls.get(i + 1);
                if (spaceEmpty(left, right)) {
                    if (walls.size() > 2) {
                        right.setOpen(true);
                        //checkedWalls.add(right);
                        //walls.remove(right);
                        DLog.info("Wall opened", DLog.Type.PLAYER_INTERACTION);
                    } else {
                        walls.remove(right);
                        DLog.info("Wall removed", DLog.Type.PLAYER_INTERACTION);
                    }
                }
            }
        }
    }

    /**
     * Function that returns true if there are no balls between two walls.
     * @param w1 Wall one
     * @param w2 Wall two
     * @return boolean if the space is empty
     */
    public boolean spaceEmpty(Wall w1, Wall w2) {
        int w1x = w1.getX() + w1.getWidth();
        int w2x = w2.getX();
        if (balls.size() > 0) {
            for (Ball b : balls) {
                if (b.getX() + b.getSize() >= w1x && b.getX() <= w2x) {
                    return false;
                }
            }
            this.flag++;
            if (this.flag > 1) {
                //this flag is because of the first second in the game,
                //in which the balls are not yet correct or something like that.
                this.flag = 0;
                return true;
            }
        }
        return false;
    }

    /**
     * Function that detects if a player is hit by a ball.
     * @return boolean if the player is hit.
     */
    public boolean ballPlayerCollision() {
        boolean res = false;
        for (Ball b : balls) {
            for (Player p : players) {
                if (p.collides(b)) {
                    res = true;
                    DLog.info(p.toString() + " is hit by a ball", DLog.Type.COLLISION);
                }
            }
        }
        return res;
    }

    /**
     * Function that detects if a player is hit by the ceiling.
     * @return boolean if the player is hit.
     */
    public boolean playerCeilingCollision() {
        boolean res = false;
        for (Wall w : walls) {
            for (Player p : players) {
                if (p.collides(w) && w.isMoveable()) {
                    res = true;
                    DLog.info(p.toString() + " is hit by the ceiling", DLog.Type.COLLISION);
                }
            }
        }
        return res;
    }

    /**
     * Loops through every object in the game to detect collisions.
     */
    public void detectCollisions() {

        collisionManager.detectCollisions();

        /*    for (Player p : getPlayers()) {
                if (powerUpManager.itemsCanCollideWith(p)) {
                    for (Collidable c : powerUpManager.getCollidables()) {
                        if (c.getBounds().getBoundsInParent().intersects(p.getBounds().getBoundsInParent())) {
                            powerUpManager.handleCollision(c, p);
                        }
                    }
                }
            }
        projectileCeilingCollision();
        ballProjectileCollision();
        playerWallCollision();
        ballWallCollision();*/
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
    public void moveWalls() {
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
        /*for (Wall w : checkedWalls) {
            w.draw(gc);
        }*/
        powerUpManager.onDraw(gc);
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
        detectCollisions();
        ballWallCheck();
        moveBalls();
        moveWalls();
        paint();
        currentTime -= 1;
        for (Player player : players) {
            player.move();
        }
        powerUpManager.onUpdate(currentTime);
    }

    /**
     * Function which is triggered if the player is hit by a ball.
     */
    public void crushed() {
        Player p = players.get(0);
        p.setLives(p.getLives() - 1);
        currentTime = time;
    }


    /**
     * Game lost, return to menu.
     */
    public void gameOver() {
        DLog.info("Game over!", DLog.Type.STATE);
        App.loadScene("/fxml/menu.fxml");
    }

    /**
     * Draw a textimage on the canvas.
     * @param i image to draw
     */
    public void drawText(Image i) {
        gc.drawImage(i, canvas.getWidth() / 2 - i.getWidth() / 2,
                canvas.getHeight() / 2 - i.getHeight());
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    public void removeBall(Ball ball) {
        balls.remove(ball);
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public void addObserver(LevelObserver observer) {
        observers.add(observer);
    }

    /**
     * Notify all observers of a state change.
     * @param state state level changed to.
     */
    public void notifyObservers(State state) {
        for (LevelObserver observer : observers) {
            observer.onLevelStateChange(state);
        }
    }

    public void startTimer() {
        timer.start();
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

    /**
     * Returns the score of a player.
     * @param player to get the score of
     * @return int score
     */
    public int getScore(int player) {
        return players.get(player).getScore();
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

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public void setFlag(int f){
        this.flag = f;
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
                        DLog.info("Player direction changed to right.", DLog.Type.PLAYER_INTERACTION);
                        last = KeyCode.RIGHT;
                    }
                    break;
                case LEFT:
                    players.get(0).setSpeed(-players.get(0).getMoveSpeed());
                    if (last != KeyCode.LEFT) {
                        DLog.info("Player direction changed to left.", DLog.Type.PLAYER_INTERACTION);
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

    public boolean isEndlessLevel() {
        return endlessLevel;
    }

    public void setEndlessLevel(boolean endlessLevel) {
        this.endlessLevel = endlessLevel;
    }

    /**
     * Class that assists in building level.
     */
    public static class Builder {

        private Canvas canvas;
        private ArrayList<Ball> balls;
        private ArrayList<Player> players;
        private ArrayList<Wall> walls;
        private int playerSpeed = PLAYERSPEED;
        private int time;

        /**
         * Constructor.
         */
        public Builder() {
            super();
        }

        public void setCanvas(Canvas canvas) {
            this.canvas = canvas;
        }
        public void setTime(int time) {
            this.time = time;
        }

        /**
         * Balls Setter.
         * @param balls balls
         * @return builder
         */
        public Builder setBalls(ArrayList<Ball> balls) {
            this.balls = balls;
            return this;
        }

        public void setWalls(ArrayList<Wall> walls) {
            this.walls = walls;
        }

        /**
         * Player Setter.
         * @param players players
         * @return builder
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
            Level level = new Level(canvas);
            Wall right = new Wall((int) canvas.getWidth(), 0, 1, (int) canvas.getHeight());
            Wall left = new Wall(0, 0, -1, (int) canvas.getHeight());
            Wall ceiling = new Wall(0, 0, (int) canvas.getWidth(), -1);
            Wall floor = new Wall(0, (int) canvas.getHeight(), (int) canvas.getWidth(), 1);

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


            level.setCollisionManager(new CollisionManager(level, new CollisionResolver(level)));
            level.setPowerUpManager(new PowerUpManager(level));
            level.setBalls(balls);
            level.setPlayers(players);
            level.setPlayerSpeed(playerSpeed);
            level.setWalls(walls);
            level.setTime(time);
            level.setCurrentTime(time);
            return level;
        }
    }

}
