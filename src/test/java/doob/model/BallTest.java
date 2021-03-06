package doob.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BallTest {
  
  @Test(expected = IllegalArgumentException.class)
  public void badBounceSpeedTest1() {
    int size = 10;
    Ball b = new Ball(0, 0, 0, 0, size);
    b.getBounceSpeed();
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBounceSpeedTest2() {
    int size = -10;
    Ball b = new Ball(0, 0, 0, 0, size);
    b.getBounceSpeed();
  }

  @Test(expected = IllegalArgumentException.class)
  public void badBounceSpeedTest3() {
    int size = 0;
    Ball b = new Ball(0, 0, 0, 0, size);
    b.getBounceSpeed();
  }
  
  @Test
  public void splitTest1() {
    Ball[] expected = new Ball[2];
    expected[0] = new Ball(100, 200, 2, -4, 64);
    expected[1] = new Ball(100, 200, -2, -4, 64);
    Ball input = new Ball(100, 200, 3, 0, 128);
    assertTrue(expected[0].equals(input.split()[0]));
    assertTrue(expected[1].equals(input.split()[1]));
  }
  
  @Test
  public void splitTest2() {
    Ball[] expected = new Ball[2];
    expected[0] = new Ball(300, 400, 2, -4, 32);
    expected[1] = new Ball(300, 400, -2, -4, 32);
    Ball input = new Ball(300, 400, 3, 5, 64);
    assertTrue(expected[0].equals(input.split()[0]));
    assertTrue(expected[1].equals(input.split()[1]));
  }
  
  @Test
  public void equalsTest1() {
    Ball a = new Ball(0,0,0,0,0);
    Ball b = new Ball(0,0,0,0,0);
    assertTrue(a.equals(b));
  }
  
  @Test
  public void equalsTest2() {
    Ball a = new Ball(0,0,0,0,0);
    assertTrue(a.equals(a));
  }
  
  @Test
  public void equalsTest3() {
    Ball a = new Ball(0,0,0,0,0);
    Ball b = new Ball(1,0,0,0,0);
    assertFalse(a.equals(b));
  }

}
