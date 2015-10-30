package doob.game.model;

import doob.App;
import doob.controller.HighscoreMenuController;
import doob.model.Player;
import doob.util.TupleTwo;

/**
 * Class to play a singleplayer game.
 */
public class CoopGame extends Game {
	
	public void initialize() {
		initNormalGame("src/main/resources/Level/MultiPlayerLevels.xml");
		level.getPlayers().get(0).setLives(Player.DOUBLE_LIVES);
		level.getPlayers().get(1).setLives(Player.DOUBLE_LIVES);
	}

	public TupleTwo<Integer> getLives() {
		return new TupleTwo<Integer>(
				level.getPlayers().get(0).getLives(),
				level.getPlayers().get(1).getLives()
		);
	}

	@Override
	public TupleTwo<Integer> getScores() {
		return new TupleTwo<Integer>(
				level.getPlayers().get(0).getScore(),
				level.getPlayers().get(1).getScore()
		);
	}

	@Override
	public void newLevel() {
		super.newLevel("coop");
	}

	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/coophighscores.xml", "Coop Mode");
		hsmc.insertScore(getScores().t0 + getScores().t1, 1);
	}
}
