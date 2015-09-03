package doob.model.game;

import doob.model.Level;

import java.util.ArrayList;

public abstract class AbstractGame {
	
	private int score;
	private int difficulty;
	private ArrayList<Level> levels;
	
	public AbstractGame(int score, int difficulty, ArrayList<Level> levels) {
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
