package doob.model;

import org.junit.Test;

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
}
