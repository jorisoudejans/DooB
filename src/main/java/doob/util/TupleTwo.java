package doob.util;

/**
 * Generic helper class that implements Tuple functionality with two objects.
 * @param <T> Variable type
 */
@SuppressWarnings("CheckStyle")
public class TupleTwo<T> {

    public T t0;
    public T t1;

    /**
     * Create a new tuple.
     * @param t0 variable one
     * @param t1 variable two.
     */
    public TupleTwo(T t0, T t1) {
        this.t0 = t0;
        this.t1 = t1;
    }

}