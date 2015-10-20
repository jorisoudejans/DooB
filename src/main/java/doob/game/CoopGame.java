package doob.game;

import java.util.ArrayList;

import doob.controller.LevelController;
import doob.level.LevelView;
import doob.util.BoundsTuple;
import javafx.scene.image.Image;
import doob.App;
import doob.controller.HighscoreMenuController;
import doob.level.LevelFactory;
import doob.model.Player;

/**
 * Class to play a singleplayer game.
 */
public class CoopGame extends Game {
	
	public void initialize() {
		initGame("src/main/resources/Level/MultiPlayerLevels.xml");
		level.getPlayers().get(0).setLives(Player.DOUBLE_LIVES);
		level.getPlayers().get(1).setLives(Player.DOUBLE_LIVES);
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
		score = level.getPlayers().get(0).getScore() + level.getPlayers().get(1).getScore();
		scoreTextView1.setText(score + "");
	}
	
	@Override
	public void newLevel() {
		ArrayList<Player> players = null;
		if (level != null) {
			level.stopTimer();
			players = level.getPlayers();
		}
		BoundsTuple bounds = new BoundsTuple(canvas.getWidth(), canvas.getHeight());
		level = new LevelFactory(levelList.get(currentLevel), bounds).build();
		level.addObserver(this);
		LevelController levelController = new LevelController(level);
		LevelView levelView = new LevelView(canvas.getGraphicsContext2D(), level);
		levelView.setLevelController(levelController);
		level.addObserver(levelView);
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
		hsmc.updateTable("src/main/resources/Highscore/coophighscores.xml", "Coop Mode");
		hsmc.insertScore(score, 1);
	}
}
