package doob.level;

import doob.model.*;

/**
 * Class to handle collision resolving.
 *
 * Created by hidde on 10/6/15.
 */
public class CollisionResolver {

    private Level level;

    public CollisionResolver(Level level) {
        this.level = level;
    }

    public void playerVersusWall(Player player, Wall wall) {

    }

    public void ballVersusWall(Ball ball, Wall w) {

    }

    public void ballVersusProjectile(Ball ball, Projectile projectile) {

    }

    /**
     * Projectile has hit the ceiling.
     * @param projectile subject
     */
    public void projectileVersusCeiling(Projectile projectile) {

    }

}
