package doob;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import doob.DLog.Type;

@RunWith(Parameterized.class)
public class DLogStateTest {

	private static String log = "Log this.";
	@Parameters
	  public static Collection<Object[]> data() {
	    return Arrays.asList(new Object[][] { { log, Type.PLAYER_INTERACTION, DateFormat.getTimeInstance().format(new Date())
			+ ": -Player interaction- Log this." }, { log, Type.COLLISION, DateFormat.getTimeInstance().format(new Date())
				+ ": -Collision- Log this." }, { log, Type.STATE, DateFormat.getTimeInstance().format(new Date())
					+ ": -State- Log this." }, { log, Type.APPLICATION, DateFormat.getTimeInstance().format(new Date())
						+ ": -App- Log this." }, { log, Type.ERROR, DateFormat.getTimeInstance().format(new Date())
							+ ": -ERROR- Log this." } });
	  }

	  private String input;
	  private Type type;
	  private String expected;

	  public DLogStateTest(String input, Type type, String expected) {
	    this.input = input;
	    this.type = type;
	    this.expected = expected;
	  }

	  @Test
	  public void statesTest() throws IOException {
		  DLog.setFile("DLogTest.log");
		  BufferedReader mReader = new BufferedReader(new InputStreamReader(new FileInputStream(
				  "DLogTest.log")));
		  DLog.i(input, type);
		  String actual = null;
		  try {
				// Skip init line.
				mReader.readLine();
				actual = mReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			assertEquals(expected, actual);
			mReader.close();
	  }

}
