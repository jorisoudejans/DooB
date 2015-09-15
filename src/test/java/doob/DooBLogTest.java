package doob;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class DooBLogTest {
  
  private Scanner sc;
  
  @Before
  public void setUp() throws FileNotFoundException {
    sc = new Scanner(new File("/DooBLog.log"));
  }

  @Test
  public void logTest() {
    String expected = "Log this.";
    DoobLog.log(expected);
    String actual = sc.nextLine();
    while(sc.hasNextLine()) {
      actual = sc.nextLine();
    }
    assertEquals(expected, actual);
  }

}
