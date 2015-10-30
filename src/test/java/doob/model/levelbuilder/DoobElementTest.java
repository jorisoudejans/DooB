package doob.model.levelbuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Abstract class with test for all doob element in the levelbuilder. *
 */
public abstract class DoobElementTest {
	
	private DoobElement el;

	private static final int TEST_VALUE_ONE = 5;
	private static final int TEST_VALUE_NEGATIVE = -5;
	private static final double OFFSET = 0.1;	

	/**
	 * Get the right element.
	 */
	@Before
	public void setup() {
		this.el = getDoobElement();
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetXCoord() {
		assertEquals(el.xCoord, el.getXCoord(), OFFSET);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetYCoord() {
		assertEquals(el.yCoord, el.getYCoord(), OFFSET);
	}
	
	/**
	 * Test setter.
	 */
	@Test
	public void testSetXCoord() {
		el.setXCoord(TEST_VALUE_ONE);
		assertEquals(el.getXCoord(), TEST_VALUE_ONE, OFFSET);
	}
	
	/**
	 * Test setter.
	 */
	@Test
	public void testSetYCoord() {
		el.setYCoord(TEST_VALUE_ONE);
		assertEquals(el.getYCoord(), TEST_VALUE_ONE, OFFSET);	
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testIsPlaced() {
		assertSame(el.placed, el.isPlaced());
	}
	
	/**
	 * Test setter.
	 */
	@Test
	public void testSetPlaced() {
		el.setPlaced(true);
		assertSame(el.isPlaced(), true);
	}
	
	/**
	 * Tests if the changed() method does not affect the x-coordinate of the object.
	 */
	@Test
	public void testChanged() {
		el.change();
		assertEquals(el.getXCoord(), el.xCoord, OFFSET);
	}
	
	/**
	 * Test if the the liesInside() method returns true iff the coordinates lie within the object.
	 */
	@Test
	public void testLiesInside() {
		assertTrue(el.liesInside(0, 1));
	}
	
	/**
	 * Test if the the liesInside() method returns flase iff 
	 * the coordinates do not lie within the object.
	 */
	@Test
	public void testLiesNotInside() {
		assertFalse(el.liesInside(TEST_VALUE_NEGATIVE,  TEST_VALUE_NEGATIVE));
	}
	
	/**
	 * Get the element you want to test.
	 * @return the doobelement to test.
	 */
	abstract DoobElement getDoobElement();
	
	/**
	 * Get the element you want to test.
	 * @return the doobelement to test.
	 */
	abstract DoobElement getDoobElementMock();
}
