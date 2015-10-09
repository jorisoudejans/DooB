package doob.model;


import doob.level.CollisionManager;
import doob.level.CollisionResolver;
import doob.level.PowerUpManager;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class that assists in building level.
 */
public class LevelBuilder {

    private Canvas canvas;
    private ArrayList<Ball> balls;
    private ArrayList<Player> players;
    private ArrayList<Wall> walls;
    private int playerSpeed = Level.PLAYER_SPEED;
    private int time;

    /**
     * Constructor.
     */
    public LevelBuilder() {
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
    public LevelBuilder setBalls(ArrayList<Ball> balls) {
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
    public LevelBuilder setPlayers(ArrayList<Player> players) {
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
        Wall left = new Wall(0, 0, 1, (int) canvas.getHeight());
        Wall ceiling = new Wall(0, 0, (int) canvas.getWidth(), 1);
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
        walls.add(floor);
        walls.add(ceiling);


        level.setCollisionManager(new CollisionManager(level, new CollisionResolver(level)));
        level.setPowerUpManager(new PowerUpManager(level));
        level.setBalls(balls);
        level.setPlayers(players);
        level.setWalls(walls);
        level.setTime(time);
        level.setCurrentTime(time);
        return level;
    }
}