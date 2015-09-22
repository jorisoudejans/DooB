package doob.model.powerup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Chance that a power-up will drop.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PowerUpChance {

    /**
     * The chance a power-up will drop, default 0.
     */
    double chance() default 0;

}