package doob.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tuple with infinite values.
 */
public class Tuple<T> {

    private List<T> valueList;

    /**
     * Construct tuple with as many arguments as you like.
     * @param values comma-seperated list of values.
     */
    public Tuple (T ...values) {
        valueList = new ArrayList<T>();
        Collections.addAll(valueList, values);
    }

    /**
     * Get value of tuple.
     * @param position position of value in tuple
     * @return Object
     */
    public T value(int position) {
        return valueList.get(position);
    }

}
