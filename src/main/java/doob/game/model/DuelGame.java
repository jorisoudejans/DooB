package doob.game.model;

import doob.App;
import doob.controller.HighscoreMenuController;
import doob.util.TupleTwo;

/**
 * Class to play a singleplayer game.
 */
public class DuelGame extends Game {

	public void initialize() {
		initNormalGame("src/main/resources/Level/MultiPlayerLevels.xml");
	}
	
	@Override
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
		super.newLevel("duel");
	}

	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/duelhighscores.xml", "Duel Mode");
		hsmc.insertScore(getScores().t0, 1);
		hsmc.insertScore(getScores().t1, 2);
	}
}
