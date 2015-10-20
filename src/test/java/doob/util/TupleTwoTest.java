package doob.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests TupleTwo.
 */
public class TupleTwoTest {

    /**
     * Test tuple with two values.
     */
    @Test
    public void testTwoValues() {
        TupleTwo<Integer> tupleTwo = new TupleTwo<Integer>(20, 42);
        assertEquals(20, (int) tupleTwo.t0);
    }

    /**
     * Test tuple with null values.
     */
    @Test
    public void testNullValues() {
        TupleTwo<Object> tupleTwo = new TupleTwo<Object>(null, null);
        assertNull(tupleTwo.t0);
    }

}