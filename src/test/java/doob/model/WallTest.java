package doob.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to test Wall.
 *
 */
public class WallTest {

	/**
	 * Tests wether getBounds return a rectangle with the right size.
	 */
	@Test
	public void getBoundsTest() {
		Wall w = new Wall(0, 0, 50, 800);
		double expectedX = 0, expectedY = 0, expectedWidth = 50 + Wall.BOUNDS_DELTA, expectedHeight = 800;
		double x = w.getBounds().getX(), y = w.getBounds().getY(), width = w
				.getBounds().getWidth(), height = w.getBounds().getHeight();
		assertTrue(expectedX == x);
		assertTrue(expectedY == y);
		assertTrue(expectedWidth == width);
		assertTrue(expectedHeight == height);
	}
	
	/**
	 * Tests wether move works correctly.
	 */
	@Test
	public void moveTest() {
		Wall actual = new Wall(0, 0, 50, 800, 100, 100, 100, 1);
		Wall expected = new Wall(100, 100, 50, 800, 100, 100, 0, 1);
		while (actual.getDuration() > 0) {
			actual.move();
		}
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests wether equals works correctly with a fixed wall.
	 */
	@Test
	public void equalsTest1() {
		Wall actual = new Wall(0, 0, 50, 800);
		Wall expected = new Wall(0, 0, 50, 800);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests wether equals works correctly with a moveable wall.
	 */
	@Test
	public void equalsTest2() {
		Wall actual = new Wall(0, 0, 50, 800, 100, 100, 100, 1);
		Wall expected = new Wall(0, 0, 50, 800, 100, 100, 100, 1);
		assertEquals(expected, actual);
	}
}
