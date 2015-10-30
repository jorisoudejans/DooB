package doob.model.levelbuilder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public abstract class DoobElementTest {
	
	private DoobElement el;
	private DoobElement mel;
	

	@Before
	public void setup() {
		this.el = getDoobElement();
		this.mel = getDoobElementMock();
	}
	
	/*@Test
	public void testChange() {
		el.change();
		verify(mel).setChanged();
	}*/
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetXCoord() {
		assertEquals(el.xCoord, el.getXCoord());
	}
	
	/**
	 * Test getter.
	 */
	@Test
	public void testGetYCoord() {
		assertEquals(el.yCoord, el.getYCoord());
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
