package doob;

import static org.junit.Assert.assertEquals;

import java.io.*;
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
  private static final String PATH = "DooBLog.log";

  /**
   * Set up testing.
   * @throws IOException if an IOException occurs.
   */
  @Before
  public void setUp() throws IOException {
    DooBLog.setFile(PATH);
    sc = new Scanner(new File(PATH));
  }

  /**
   * One line log test.
   */
  @Test
  public void testOneLine() {
    String input = "Log this.";
    String expected = DateFormat.getTimeInstance().format(new Date()) + ": Log this.";
    DooBLog.i(input);
    String actual = sc.nextLine();
    while (sc.hasNextLine()) {
      actual = sc.nextLine();
    }
    assertEquals(expected, actual);
  }

  /**
   * Test the emptyFile() method of DooBLog.
   */
  @Test
  public void testEmptyFIle() {
    DooBLog.i("hoi");
    DooBLog.i("hoi 2");
    try {
      DooBLog.emptyFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      BufferedReader fileReader = new BufferedReader(
              new InputStreamReader(
                      new FileInputStream(PATH)
              )
      );
      assertEquals(
              fileReader.readLine(),
              DateFormat
                      .getTimeInstance()
                      .format(new Date())
                      + ": "
                      + DooBLog.LOG_CREATED_MESSAGE
                      + PATH
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
