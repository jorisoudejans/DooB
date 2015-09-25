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

/**
 * Parameterized test class to test all types of logging.
 */
@RunWith(Parameterized.class)
public class DLogTypeTest {

	private static String log = "Log this.";
	private static String time = DateFormat.getTimeInstance()
			.format(new Date());

	/**
	 * Types that are tested.
	 * 
	 * @return parameters
	 */
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ log, Type.PLAYER_INTERACTION,
						" -Player interaction- Log this." },
				{ log, Type.COLLISION, " -Collision- Log this." },
				{ log, Type.STATE, " -State- Log this." },
				{ log, Type.APPLICATION, " -App- Log this." },
				{ log, Type.ERROR, " -ERROR- Log this." } });
	}

	private String input;
	private Type type;
	private String expected;

	/**
	 * Constructor.
	 * 
	 * @param input
	 *            String
	 * @param type
	 *            Type
	 * @param expected
	 *            String
	 */
	public DLogTypeTest(String input, Type type, String expected) {
		this.input = input;
		this.type = type;
		this.expected = expected;
	}

	/**
	 * Tests the types.
	 * 
	 * @throws IOException
	 *             exception
	 */
	@Test
	public void statesTest() throws IOException {
		DLog.setFile("DLogTest.log");
		BufferedReader mReader = new BufferedReader(new InputStreamReader(
				new FileInputStream("DLogTest.log")));
		DLog.info(input, type);
		String actual = null;
		try {
			// Skip init line.
			mReader.readLine();
			actual = mReader.readLine().split(":")[3];
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(expected, actual);
		mReader.close();
	}

}
