package doob.game;

/**
 * Interface needed for Game Extensions.
 */
public interface GameMode {
	
	/**
	 * Initialize the gamemode.
	 */
	void initialize();
	
	/**
	 * Handle how to update the score.
	 */
	void updateScore();
	
	/**
	 * Handle how to update the lives.
	 */
	void updateLives();
	
	/**
	 * handle how a new level is initialized.
	 */
	void newLevel();
	
	/**
	 * Handle how to load the highscores.
	 */
	void loadHighscores();

}
