package doob;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for test.
 */
public class DooBLogTest {
  
  private Scanner sc;

  /**
   * Set up testing.
   * @throws IOException if an IOException occurs.
   */
  @Before
  public void setUp() throws IOException {
    DooBLog.setFile("DooBLog.log");
    sc = new Scanner(new File("DooBLog.log"));
  }

  /**
   * First log test.
   */
  @Test
  public void logTestOneLine() {
    String input = "Log this.";
    String expected = DateFormat.getTimeInstance().format(new Date()) + ": Log this.";
    DooBLog.i(input);
    String actual = sc.nextLine();
    while (sc.hasNextLine()) {
      actual = sc.nextLine();
    }
    assertEquals(expected, actual);
  }

}
