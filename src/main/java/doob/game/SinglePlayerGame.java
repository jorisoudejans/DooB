package doob.game;

import javafx.scene.image.Image;
import doob.App;
import doob.controller.HighscoreMenuController;
import doob.level.LevelFactory;
import doob.model.Player;

/**
 * Class to play a singleplayer game.
 */
public class SinglePlayerGame extends Game implements GameMode {

	@Override
	public void initialize() {
		initGame("src/main/resources/Level/SinglePlayerLevels.xml");
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
		Player p = null;
		if (level != null) {
			level.stopTimer();
			p = level.getPlayers().get(0);
		}
		level = new LevelFactory(levelList.get(currentLevel), canvas).build();
		level.addObserver(this);
		if (p != null) {
			int lives = p.getLives();
			int score = p.getScore();
			level.getPlayers().get(0).setLives(lives);
			level.getPlayers().get(0).setScore(score);
		}
		readOptions();
	}
	
	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/highscores.xml", "SinglePlayer Mode");
		hsmc.insertScore(score, 1);
	}
}
