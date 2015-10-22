package doob.game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import doob.App;
import doob.controller.HighscoreMenuController;
import doob.level.LevelFactory;
import doob.model.Player;

/**
 * Class to play a singleplayer game.
 */
public class DuelGame extends Game {

	public void initialize() {
		initGame("src/main/resources/Level/MultiPlayerLevels.xml");
	}
	
	@Override
	public void updateLives() {
		gc.clearRect(0, 0, lives1.getWidth(), lives1.getHeight());
		gc2.clearRect(0, 0, lives2.getWidth(), lives2.getHeight());
		int lives = level.getPlayers().get(0).getLives();
		int lives2 = level.getPlayers().get(1).getLives();
		
		for (int i = 0; i < lives; i++) {
			gc.drawImage(new Image("/image/heart.png"), i
					* HEART_SPACE, HEART_Y);
		}
		
		for (int i = 0; i < lives2; i++) {
			gc2.drawImage(new Image("/image/heart.png"), i
					* HEART_SPACE, HEART_Y);
		}
	}

	@Override
	public void updateScore() {
		score = level.getPlayers().get(0).getScore();
		score2 = level.getPlayers().get(1).getScore();
		scoreTextView1.setText(score + "");
		scoreTextView2.setText(score2 + "");
	}
	
	@Override
	public void newLevel() {
		ArrayList<Player> players = null;
		if (level != null) {
			level.stopTimer();
			players = level.getPlayers();
		}
		level = new LevelFactory(levelList.get(currentLevel), canvas).build();
		level.addObserver(this);
		if (players != null) {
			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				int lives = p.getLives();
				int score = p.getScore();
				level.getPlayers().get(i).setLives(lives);
				level.getPlayers().get(i).setScore(score);
			}
		}
		readOptions();
	}
	
	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/duelhighscores.xml", "Duel Mode");
		hsmc.insertScore(score2, 2);
		hsmc.insertScore(score, 1);
	}
}
