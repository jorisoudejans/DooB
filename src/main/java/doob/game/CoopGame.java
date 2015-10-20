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
public class CoopGame extends Game implements GameMode {
	

	@Override
	public void initialize() {
		initGame("src/main/resources/Level/MultiPlayerLevels.xml");
		level.getPlayers().get(0).setLives(Player.DOUBLE_LIVES);
		level.getPlayers().get(1).setLives(Player.DOUBLE_LIVES);
	}
	
	/**
	 * Updates the amount of lives every gamestep.
	 */
	@Override
	public void updateLives() {
		gc.clearRect(0, 0, lives1.getWidth(), lives1.getHeight());
		int lives = level.getPlayers().get(0).getLives();
		
		for (int i = 0; i < lives; i++) {
			gc.drawImage(new Image("/image/heart.png"), i
					* HEART_SPACE, HEART_Y);
		}
	}

	/**
	 * Updates the score every gamestep.
	 */
	@Override
	public void updateScore() {
		score = level.getPlayers().get(0).getScore() + level.getPlayers().get(1).getScore();
		scoreTextView1.setText(score + "");
	}
	
	/**
	 * Resets the level depending on currentLevel. Only keeps amount of lives.
	 */
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
		level.getCollisionManager().getCollisionResolver().setcoopMode(true);
		readOptions();
	}
	
	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/coophighscores.xml", "Coop Mode");
		hsmc.insertScore(score, 1);
	}
}
