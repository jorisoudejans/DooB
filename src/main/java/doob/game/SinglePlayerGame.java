package doob.game;

import javafx.scene.image.Image;
import doob.App;
import doob.controller.HighscoreMenuController;
import doob.model.levelbuilder.LevelReader;

/**
 * Class to play a singleplayer game.
 */
public class SinglePlayerGame extends Game {

	/**
	 * Load the list of single-player levels.
	 */
	public void initialize() {
		initGame("src/main/resources/Level/SinglePlayerLevels.xml");
	}
	
	/**
	 * Load the list of custom made levels.
	 */
	public void initializeCustom() {
		LevelReader lr = new LevelReader();
		initCustomGame(lr.makeCustomLevelList());		
	}
	
	@Override
	public void updateLives() {
		gc.clearRect(0, 0, lives1.getWidth(), lives1.getHeight());
		int lives = level.getPlayers().get(0).getLives();
		
		for (int i = 0; i < lives; i++) {
			gc.drawImage(new Image("/image/heart.png"), i
					* HEART_SPACE, HEART_Y);
		}
	}

	@Override
	public void updateScore() {
		score = level.getPlayers().get(0).getScore();
		scoreTextView1.setText(score + "");
	}

	@Override
	public void newLevel() {
		super.newLevel("single");
	}

	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/highscores.xml", "SinglePlayer Mode");
		hsmc.insertScore(score, 1);
	}
}
