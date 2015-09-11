package doob.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BounceSpeedTest {
  
  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] { { 8, -6 }, { 16, -8 }, { 32, -10 }, { 64, -12 }, { 128, -14 },
        { 256, -16 } });
  }

  private int input;
  private int expected;

  public BounceSpeedTest(int input, int expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void goodBounceSpeedTest() {
    Ball b = new Ball(0, 0, 0, 0, input);
    assertEquals(expected, b.getBounceSpeed());
  }
}
