package doob.model.levelbuilder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test class for the wall elements in the levelbuilder. *
 */
public class WallElementTest extends DoobElementTest {
	
	private WallElement we;
	private WallElement mwe;
	
	private static final int TEST_VALUE_ONE = 5;
	private static final int TEST_VALUE_TWO = 4;
	
	@Override
	DoobElement getDoobElement() {
		this.we = new WallElement(0);
		return this.we;
	}

	@Override
	DoobElement getDoobElementMock() {
		this.mwe = Mockito.mock(WallElement.class);
		return this.mwe;
	}

	/**
	 * Test the getAMount() method if the program recognizes the right amount of elements.
	 */
	@Test
	public void testGetAmount() {
		ArrayList<DoobElement> ar = new ArrayList<DoobElement>();
		BallElement b = new BallElement(0, 0, TEST_VALUE_ONE, 0, 0);
		WallElement w = new WallElement(0);
		ar.add(b);
		ar.add(b);
		ar.add(w);
		assertEquals(WallElement.getAmount(ar), 1);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetWidth() {
		assertEquals(we.getWidth(), WallElement.WALL_WIDTH);
	}
	
	/**
	 * Test setter.
	 */
	@Test
	public void testSetWidth() {
		we.setWidth(TEST_VALUE_ONE);
		assertEquals(we.getWidth(), TEST_VALUE_ONE);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetHeight() {
		assertEquals(we.getHeight(), WallElement.WALL_HEIGHT);
	}
	
	/**
	 * Test setter.
	 */
	@Test
	public void testSetHeight() {
		we.setHeight(TEST_VALUE_TWO);
		assertEquals(we.getHeight(), TEST_VALUE_TWO);
	}
}
