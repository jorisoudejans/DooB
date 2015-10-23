package doob.model;

import doob.util.BoundsTuple;

import java.util.Random;

/**
 * A survival level.
 * Created by hidde on 10/21/15.
 */
public class SurvivalLevel extends Level {

    private int nextWaveHP = 0;
    private static final int DIFFICULTY_TIME = 1000;
    private static final int SMALLEST_BALL_SIZE = 16;
    private BoundsTuple bounds;

    /**
     * Init new level with size bounds.
     *
     * @param bounds width and height
     */
    public SurvivalLevel(BoundsTuple bounds) {
        super(bounds);
        this.bounds = bounds;
    }

    @Override
    public void update() {
        super.update();
        double cTime = getCurrentTime();
        if (cTime > DIFFICULTY_TIME) {
            setCurrentTime(cTime - DIFFICULTY_TIME);
            nextWaveHP++;
        }
        if (totalBallHitpoints() <= nextWaveHP) {
            spawnBalls(System.currentTimeMillis());
        }
    }

    /**
     * Adds one to time.
     * @return one
     */
    @Override
    protected int getTimeMutation() {
        return 1;
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
        for (Ball b: getBalls()) {
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
            this.getBalls().add(ball);
        }
    }
}
