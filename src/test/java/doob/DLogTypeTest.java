package doob;

import doob.DLog.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


/**
 * Parameterized test class to test all types of logging.
 *
 */
@RunWith(Parameterized.class)
public class DLogTypeTest {

	private static final String LOG = "Log this.";
	private static final int POSITION_AFTER_TIMESTAMP = 3;

	private DLog dLog;

	@Before
	public void setUp() {
		dLog = DLog.getInstance();
		dLog.setFile("DLogTest.log");
	}

	/**
	*
	 * Types that are tested.
	 * 
	 * @return parameters
	 */
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ LOG, Type.PLAYER_INTERACTION,
						" -Player interaction- Log this." },
				{ LOG, Type.COLLISION, " -Collision- Log this." },
				{ LOG, Type.STATE, " -Event- Log this." },
				{ LOG, Type.APPLICATION, " -App- Log this." },
				{ LOG, Type.ERROR, " -ERROR- Log this." } });
	}

	private String input;
	private Type type;
	private String expected;

		  /**
	   * Constructor.
	   * @param input String
	   * @param type Type
	   * @param expected String
	   */
	
	  public DLogTypeTest(String input, Type type, String expected) {
	    this.input = input;
	    this.type = type;
	    this.expected = expected;
	  }

	  /**
	   * Tests the types.
	   * @throws IOException exception
	   */
	  @Test
	  public void statesTest() throws IOException {
		  BufferedReader mReader = new BufferedReader(new InputStreamReader(new FileInputStream(
				  "DLogTest.log")));
		  dLog.info(input, type);
		  String actual = null;
		  try {
				// Skip init line.
				mReader.readLine();
				actual = mReader.readLine().split(":")[POSITION_AFTER_TIMESTAMP];
			} catch (IOException e) {
				e.printStackTrace();
			}
			assertEquals(expected, actual);
			mReader.close();
	  }
}

