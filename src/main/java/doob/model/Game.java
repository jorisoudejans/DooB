package doob.model;

import java.util.ArrayList;

/**
 * Abstract class which is extended by SinglePlayerGame and MultiPlayerGame.
 * @author Cas
 *
 */
public abstract class Game {
	
	private int score;
	private int difficulty;
	private ArrayList<Level> levels;
	
	/**
	 * Simple constructor.
	 * @param score The current score
	 * @param difficulty An integer which describes the difficulty of the game
	 * @param levels The list of available levels for the game
	 */
	public Game(int score, int difficulty, ArrayList<Level> levels) {
		this.score = score;
		this.difficulty = difficulty;
		this.levels = levels;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}
}
