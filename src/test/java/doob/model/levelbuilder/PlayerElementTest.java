package doob.model.levelbuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test the player elements in the levelbuilder.
 */
public class PlayerElementTest extends DoobElementTest {

	private PlayerElement pe;
	private PlayerElement mpe;	

	private static final int TEST_VALUE_ONE = 5;
	private static final int TEST_VALUE_BIG = 600;
	
	@Override
	public void testLiesInside() {
		assertTrue(pe.liesInside(0, TEST_VALUE_BIG));		
	}

	@Override
	DoobElement getDoobElement() {
		this.pe = new PlayerElement(0, new ArrayList<DoobElement>());
		return this.pe;
	}

	@Override
	DoobElement getDoobElementMock() {
		this.mpe = Mockito.mock(PlayerElement.class);
		return this.mpe;
	}
	
	/**
	 * Tests if the getAmount() method returns the right amount of the type.
	 */
	@Test
	public void testGetAmount() {
		ArrayList<DoobElement> ar = new ArrayList<DoobElement>();
		PlayerElement p = new PlayerElement(0, ar);
		WallElement w = new WallElement(0);
		ar.add(p);
		ar.add(p);
		ar.add(w);
		assertEquals(PlayerElement.getAmount(ar), 2);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetWidth() {
		assertEquals(pe.getWidth(), PlayerElement.PLAYER_WIDTH);
	}

	/**
	 * Test setter.
	 */
	@Test
	public void testSetWidth() {
		pe.setWidth(TEST_VALUE_ONE);
		assertEquals(pe.getWidth(), TEST_VALUE_ONE);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetHeight() {
		assertEquals(pe.getHeight(), PlayerElement.PLAYER_HEIGHT);
	}
	
	/**
	 * Test setter.
	 */
	@Test
	public void testSetHeight() {
		pe.setHeight(TEST_VALUE_BIG);
		assertEquals(pe.getHeight(), TEST_VALUE_BIG);
	}

}
