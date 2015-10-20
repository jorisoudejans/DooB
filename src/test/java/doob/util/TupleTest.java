package doob.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created on 20/10/15 by Joris.
 */
public class TupleTest {

    /**
     * Test Tuple with two values.
     */
    @Test
    public void testTwoValues() {
        Tuple<Integer> tupleTwo = new Tuple<Integer>(20, 42);

        assertEquals((int) tupleTwo.v(1), 42);
    }

    @Test
    public void testFourValues() {
        Tuple<Integer> tupleFour = new Tuple<Integer>(50, 20, 1000, 39);

        assertEquals((int) tupleFour.v(3), 39);
    }
}