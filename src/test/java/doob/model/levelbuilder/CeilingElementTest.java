package doob.model.levelbuilder;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test the ceiling elements in the levelbuilder.
 */
public class CeilingElementTest extends DoobElementTest {

	private CeilingElement ce;
	private CeilingElement mce;

	private static final int TEST_VALUE_ONE = 5;
	private static final int TEST_VALUE_TWO = 4;

	@Override
	DoobElement getDoobElement() {
		this.ce = new CeilingElement(0);
		return this.ce;
	}

	@Override
	DoobElement getDoobElementMock() {
		this.mce = Mockito.mock(CeilingElement.class);
		return this.mce;
	}
	
	/**
	 * Tests the getAmount() method if the right amount of the type is returned.
	 */
	@Test
	public void testGetAmount() {
		ArrayList<DoobElement> ar = new ArrayList<DoobElement>();
		CeilingElement c = new CeilingElement(0);
		WallElement w = new WallElement(0);
		ar.add(c);
		ar.add(c);
		ar.add(w);
		assertEquals(ce.getAmount(ar), 2);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetWidth() {
		assertEquals(ce.getWidth(), CeilingElement.CEILING_WIDTH);
	}
	
	/**
	 * Test setter.
	 */
	@Test
	public void testSetWidth() {
		ce.setWidth(TEST_VALUE_ONE);
		assertEquals(ce.getWidth(), TEST_VALUE_ONE);
	}

	/**
	 * Test getter.
	 */
	@Test
	public void testGetHeight() {
		assertEquals(ce.getHeight(), CeilingElement.CEILING_HEIGHT);
	}

	/**
	 * Test setter.
	 */
	@Test
	public void testSetHeight() {
		ce.setHeight(TEST_VALUE_TWO);
		assertEquals(ce.getHeight(), TEST_VALUE_TWO);
	}

}
