package doob.level;

import com.google.common.reflect.ClassPath;
import doob.DLog;
import doob.model.*;
import doob.model.powerup.PowerUp;
import doob.model.powerup.PowerUpChance;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages powerups on level. Loads classes etc
 *
 * Created by hidde on 10/5/15.
 */
public class PowerUpManager {

    private ArrayList<Class<?>> availablePowerups;
    private ArrayList<PowerUp> powerupsOnScreen;
    private ArrayList<PowerUp> powerupsOnScreenToRemove;
    private ArrayList<PowerUp> activePowerups;

    private Level level;

    public PowerUpManager(Level level) {
        availablePowerups = new ArrayList<Class<?>>();
        powerupsOnScreen = new ArrayList<PowerUp>();
        powerupsOnScreenToRemove = new ArrayList<PowerUp>();
        activePowerups = new ArrayList<PowerUp>();

        this.level = level;
    }

    public void onInit() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith("doob.model.powerup")) {
                    final Class<?> clazz = info.load();
                    if (clazz.getSuperclass() != null && clazz.getSuperclass().equals(PowerUp.class)) {
                        availablePowerups.add(clazz);
                    }
                    // do something with your clazz
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCollide(Object collider, Object collidee) {
        if (collider instanceof Projectile && collidee instanceof Ball) {
            Ball ball = (Ball)collidee;
            spawnPowerups(ball.getX(), ball.getY());
        }
    }

    public void spawnPowerups(double locationX, double locationY) {
        Random random = new Random();
        for (Class<?> powerup : availablePowerups) {
            double rand = random.nextDouble();
            PowerUpChance chanceAnnotation = powerup.getAnnotation(PowerUpChance.class);
            if (rand < chanceAnnotation.chance()) {
                // drop powerup
                try {
                    PowerUp p = (PowerUp) powerup.newInstance();
                    Image sprite = new Image(p.spritePath());
                    p.setSpriteImage(sprite);
                    p.setLocationX(locationX);
                    p.setLocationY(locationY);
                    powerupsOnScreen.add(p);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleCollision(Collidable collider, Collidable collidee) {
        PowerUp powerup = (PowerUp)collider;
        Player player = (Player)collidee;
        DLog.info(player.toString() + " is hit by a powerup", DLog.Type.COLLISION);
        powerup.onActivate(level, player);
        activePowerups.add(powerup);
        powerupsOnScreenToRemove.add(powerup);
    }

    public boolean itemsCanCollideWith(Collidable collider) {
        return collider instanceof Player;
    }

    public ArrayList<PowerUp> getCollidables() {
        return powerupsOnScreen;
    }

    public ArrayList<PowerUp> getPowerupsOnScreen() {
        return powerupsOnScreen;
    }

    public void onUpdate(double time) {
        for (PowerUp p : powerupsOnScreenToRemove) {
            powerupsOnScreen.remove(p);
        }
        powerupsOnScreenToRemove = new ArrayList<PowerUp>();
        ArrayList<PowerUp> toRemoveWait = new ArrayList<PowerUp>();
        for (PowerUp powerup : powerupsOnScreen) { // move powerups down
            if (powerup.getLocationY() < level.getFloor().getY() - 30) {
                powerup.setLocationY(powerup.getLocationY() + 2);
            }
            powerup.tickWait();
            if (powerup.getCurrentWaitTime() <= 0) {
                toRemoveWait.add(powerup);
            }
        }
        for (PowerUp p : toRemoveWait) {
            powerupsOnScreen.remove(p);
        }
        ArrayList<PowerUp> toRemove = new ArrayList<PowerUp>();
        for (PowerUp powerUp : activePowerups) { // deactivate active powerups, if needed
            powerUp.tickActive();
            if (powerUp.getActiveTime() <= 0) {
                powerUp.onDeactivate(level);
                toRemove.add(powerUp);
            }
        }
        for (PowerUp p : toRemove) {
            activePowerups.remove(p);
        }
    }

    public void onDraw(GraphicsContext gc) {
        for (PowerUp powerup : powerupsOnScreen) {
            gc.drawImage(powerup.getSpriteImage(), powerup.getLocationX(), powerup.getLocationY());
        }
    }
}
