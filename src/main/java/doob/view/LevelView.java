package doob.view;

import doob.controller.LevelController;
import doob.model.level.PowerUpManager;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import doob.model.Ball;
import doob.model.level.Level;
import doob.model.Player;
import doob.model.Projectile;
import doob.model.Wall;

/**
 * Created on 16/10/15 by Joris.
 */
public class LevelView implements Observer {

    private GraphicsContext gc;
    private Canvas canvas;
    private Level level;

    /**
     * Construct new LevelView.
     * @param gc graphicsContext to draw upon
     * @param level level
     */
    public LevelView(GraphicsContext gc, final Level level) {
        this.gc = gc;
        if (gc != null) {
            this.canvas = gc.getCanvas();
            this.canvas.setFocusTraversable(true);


            canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent key) {
                    for (Player p : level.getPlayers()) {
                        if (p.isAlive()) {
                            if (p.getControlKeys().isMoveKey(key.getCode())) {
                                p.setSpeed(0);
                            }
                        }
                    }
                }
            });

            canvas.requestFocus();
        }
        this.level = level;
    }

    /**
     * Adds the given controller as a listener for user input. (key presses)
     * @param controller the level controller
     */
    public void setLevelController(LevelController controller) {
        this.canvas.setOnKeyPressed(controller);
    }

    /**
     * Paint all views.
     * @param players players
     * @param balls balls
     * @param walls walls
     * @param powerUpManager powerupManager
     * @param lastEvent lastEvent
     */
    public void draw(
            List<Player> players,
            List<Ball> balls,
            List<Wall> walls,
            PowerUpManager powerUpManager,
            Level.Event lastEvent
    ) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawPlayers(players);
        for (Ball ball : balls) {
            ball.draw(gc);
        }
        for (Wall wall : walls) {
            wall.draw(gc);
        }
        powerUpManager.onDraw(gc);
        drawEvent(lastEvent);
    }

    /**
     * Draws image on the canvas in case of event.
     * @param lastEvent event
     */
    private void drawEvent(Level.Event lastEvent) {
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
     * Draws all players to the canvas.
     * @param players players
     */
    private void drawPlayers(List<Player> players) {
        for (Player player : players)  {
            if (player.isAlive()) {
                player.draw(gc);
                for (Projectile projectile : player.getProjectiles()) {
                    gc.drawImage(projectile.getImg(), projectile.getXCoord(), projectile.getYCoord());
                    projectile.draw(gc);
                }
            }
        }
    }

    /**
     * Move objects.
     * @param players players
     * @param balls balls
     * @param walls walls
     */
    public void move(List<Player> players, List<Ball> balls, List<Wall> walls) {
        movePlayers(players);
        moveBalls(balls);
        moveWalls(walls);
    }

    /**
     * Handle the movements of players.
     * @param players players
     */
    private void movePlayers(List<Player> players) {
        for (Player player : players) {
            if (player.isAlive()) {
                player.move();
                moveProjectiles(player, player.getProjectiles());
            }
        }
    }

    /**
     * Handle the movements of projectiles.
     * @param player players
     * @param projectiles projectiles
     */
    private void moveProjectiles(Player player, List<Projectile> projectiles) {
        for (Projectile projectile: player.getProjectiles()) {
            if (!(projectile.getState() == Projectile.State.FROZEN && level.isProjectileFreeze())) {
                projectile.move();
            }
        }
    }

    /**
     * Handle the movements of walls.
     * @param walls walls
     */
    private void moveWalls(List<Wall> walls) {
        for (Wall wall : walls) {
            if (wall.isMoveable()) {
                wall.move();
            }
        }
    }

    /**
     * Handle the movements of balls.
     * @param balls balls
     */
    private void moveBalls(List<Ball> balls) {
        if (level.isBallFreeze()) {
            return;
        }
        for (Ball b : balls) {
            b.move();
        }
    }

    /**
     * Draw a textimage on the canvas.
     * @param i image to draw
     */
    private void drawText(Image i) {
        gc.drawImage(i, canvas.getWidth() / 2 - i.getWidth() / 2,
                canvas.getHeight() / 2 - i.getHeight());
    }

    /**
     * Updates the level view.
     * @param o the level object
     * @param arg optional argument Level.Event
     */
    @Override
    public void update(Observable o, Object arg) {
        Level level = (Level) o;
        Level.Event event = Level.Event.NULL;
        if (arg instanceof Level.Event) {
            event = (Level.Event) arg;
        }
        draw(level.getPlayers(), level.getBalls(), level.getWalls(), level.getPowerUpManager(), event);
        move(level.getPlayers(), level.getBalls(), level.getWalls());
    }
}
