package doob;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for test.
 */
public class DLogTest {

	private BufferedReader mReader;
	private static final String PATH = "DLogTest.log";

	/**
	 * Set up testing.
	 * 
	 * @throws IOException
	 *             if an IOException occurs.
	 */
	@Before
	public void setUp() throws IOException {
		DLog.setFile(PATH);
		mReader = new BufferedReader(new InputStreamReader(new FileInputStream(
				PATH)));

	}

	/**
	 * Test setFile method.
	 */
	@Test
	public void testSetFile() {
		try {
			assertEquals(DateFormat.getTimeInstance().format(new Date())
					+ ": -App- Log file created. Path is " + PATH,
					mReader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * One line log test.
	 */
	@Test
	public void testOneLine() {
		String input = "Log this.";
		String expected = DateFormat.getTimeInstance().format(new Date())
				+ ": Log this.";
		DLog.info(input);
		String actual = null;
		try {
			// Skip init line.
			mReader.readLine();
			actual = mReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	/**
	 * Error log test.
	 */
	@Test
	public void testErrorLog() {
		String input = "Error.";
		String expected = DateFormat.getTimeInstance().format(new Date())
				+ ": -ERROR- " + "Error.";
		DLog.e(input);
		String actual = null;
		try {
			// Skip init line.
			mReader.readLine();
			actual = mReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	/**
	 * Test for when a property is not enabled.
	 * 
	 * @throws IOException exception
	 */
	@Test
	public void notEnabledTest() throws IOException {
		PrintWriter printer = new PrintWriter(new FileWriter("DooB.properties"));
		printer.println("logEnabled=no");
		printer.println("logTypesEnabled=");
		printer.close();
		DLog.invalidateProperties();
		String input = "Log this.";
		DLog.info(input);
		String actual = "";
		try {
			// Skip init line.
			mReader.readLine();
			actual = mReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(null, actual);
		printer = new PrintWriter(new FileWriter("DooB.properties"));
		printer.println("logEnabled=yes");
		printer.println("logTypesEnabled=STATE, COLLISION, ERROR, PLAYER_INTERACTION, APPLICATION");
		printer.close();
	}

	/**
	 * Test the emptyFile() method of DLog.
	 */
	@Test
	public void testEmptyFIle() {
		DLog.info("hoi");
		DLog.info("hoi 2");
		try {
			DLog.emptyFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader fileReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(PATH)));
			assertEquals(DateFormat.getTimeInstance().format(new Date()) + ": "
					+ "-App- " + DLog.LOG_CREATED_MESSAGE + PATH,
					fileReader.readLine());
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close buffered reader after testing.
	 */
	@After
	public void tearDown() {
		try {
			mReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
