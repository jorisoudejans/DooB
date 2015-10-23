package doob.util;

/**
 * Tuple for width and height.
 * Created by hidde on 10/20/15.
 */
public class BoundsTuple extends TupleTwo<Double> {


    /**
     * Create a new tuple.
     *
     * @param t0 width
     * @param t1 height
     */
    public BoundsTuple(Double t0, Double t1) {
        super(t0, t1);
    }

    /**
     * Returns the width. Helper function to improve readability.
     * @return width
     */
    public Double getWidth() {
        return t0;
    }

    /**
     * Returns the height. Helper function to improve readability.
     * @return height
     */
    public Double getHeight() {
        return t1;
    }
}
