package doob.model.levelbuilder;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test the ball elements of the levelbuilder.
 * @author Cas
 *
 */
public class BallElementTest extends DoobElementTest {
	
	private BallElement be;
	private BallElement mbe;
	
	private static final int TEST_VALUE_ONE = 5;
	private static final double OFFSET = 0.1;
	
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
		assertEquals(BallElement.getAmount(ar), 2);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testgetSize() {
		assertEquals(be.getSize(), TEST_VALUE_ONE);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetSpeedX() {
		assertEquals(be.getSpeedX(), 0, OFFSET);
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetSpeedY() {
		assertEquals(be.getSpeedY(), 0, OFFSET);
	}

	@Override
	DoobElement getDoobElement() {
		this.be = new BallElement(0, 0, TEST_VALUE_ONE, 0, 0);
		return this.be;
	}

	@Override
	DoobElement getDoobElementMock() {
		this.mbe = Mockito.mock(BallElement.class);
		return this.mbe;
	}

}
