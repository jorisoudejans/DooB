package doob.level;

import doob.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Created on 16/10/15 by Joris.
 */
public class ObjectDrawer {

    private GraphicsContext gc;
    Canvas canvas;
    Level level;

    /**
     * Construct new ObjectDrawer.
     * @param gc graphicsContext to draw upon
     * @param level level
     */
    public ObjectDrawer(GraphicsContext gc, Level level) {
        this.gc = gc;
        if (gc != null) {
            this.canvas = gc.getCanvas();
        }
        this.level = level;
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
                    gc.drawImage(projectile.getImg(), projectile.getX(), projectile.getY());
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

}
