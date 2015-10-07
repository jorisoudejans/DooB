package doob.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import doob.model.Score;

/**
 *	Testclass for HighscoreController.
 */
public class HighScoreControllerTest {

	/**
	 * Test the read method.
	 */
	@Test
	public void readTest() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest1.xml");
		ArrayList<Score> expected = new ArrayList<Score>();
		for (int i = 9; i >= 0; i--) {
			expected.add(new Score(i + "nd", i));
		}
		ArrayList<Score> actual = hsc.read();
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void writeTest() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest3.xml");
		ArrayList<Score> expected = new ArrayList<Score>();
		for (int i = 9; i >= 0; i--) {
			expected.add(new Score(i + "nd", i));
		}
		hsc.setHighscores(expected);
		hsc.write();
		ArrayList<Score> actual = hsc.read();
		assertEquals(expected, actual);
	}

	/**
	 * Test the highScoreIndex method with highest score.
	 */
	@Test
	public void highScoreIndexTest1() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest2.xml");
		hsc.read();
		assertEquals(0, hsc.highScoreIndex(13));
	}

	/**
	 * Test the highScoreIndex method with middle score.
	 */
	@Test
	public void highScoreIndexTest2() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest2.xml");
		hsc.read();
		assertEquals(6, hsc.highScoreIndex(6));
	}

	/**
	 * Test the highScoreIndex method with lowest score.
	 */
	@Test
	public void highScoreIndexTest3() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest2.xml");
		hsc.read();
		assertEquals(9, hsc.highScoreIndex(2));
	}

	/**
	 * Test the highScoreIndex method with a too low score.
	 */
	@Test
	public void highScoreIndexTest4() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest2.xml");
		hsc.read();
		assertEquals(-1, hsc.highScoreIndex(1));
	}
	
	/**
	 * Test the highScoreIndex method with an empty highscore list.
	 */
	@Test
	public void highScoreIndexTest5() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest2.xml");
		assertEquals(0, hsc.highScoreIndex(1));
	}
	
	/**
	 * Test the addScore method with an empty highscore list.
	 */
	@Test
	public void addScoreTest1() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest1.xml");
		
		ArrayList<Score> expected = new ArrayList<Score>();
		expected.add(new Score("name", 100));
		
		hsc.addScore(new Score("name", 100), 0);
		ArrayList<Score> actual = hsc.getHighscores();
		assertEquals(expected, actual);
	}

	/**
	 * Test the addScore method with a full highscore list.
	 */
	@Test
	public void addScoreTest2() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest1.xml");
		
		ArrayList<Score> expected = new ArrayList<Score>();
		expected.add(new Score("name", 1000));
		for (int i = 9; i > 0; i--) {
			expected.add(new Score(i + "nd", i * 100));
		}
		for (int i = 0; i < 10; i++) {
			hsc.addScore(new Score(9 - i + "nd", (9 - i) * 100), i);
		}
		
		hsc.addScore(new Score("name", 1000), 0);
		ArrayList<Score> actual = hsc.getHighscores();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test the addScore method with a too low index.
	 */
	@Test
	public void addScoreTest3() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest1.xml");
		
		ArrayList<Score> expected = new ArrayList<Score>();
		
		hsc.addScore(new Score("name", 100), -1);
		ArrayList<Score> actual = hsc.getHighscores();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test the addScore method with a too high index.
	 */
	@Test
	public void addScoreTest4() {
		HighscoreController hsc = new HighscoreController(
				"src/test/resources/Highscore/highscorestest1.xml");
		
		ArrayList<Score> expected = new ArrayList<Score>();
		
		hsc.addScore(new Score("name", 100), 2);
		ArrayList<Score> actual = hsc.getHighscores();
		assertEquals(expected, actual);
	}
}
