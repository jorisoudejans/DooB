package doob.game.model;

import doob.App;
import doob.controller.HighscoreMenuController;
import doob.model.levelbuilder.LevelReader;
import doob.util.TupleTwo;

/**
 * Class to play a singleplayer game.
 */
public class CustomGame extends Game {

	/**
	 * Load the list of single-player levels.
	 */
	public void initialize() {
		LevelReader lr = new LevelReader();
		initCustomGame(lr.makeCustomLevelList());
	}
	
	@Override
	public TupleTwo<Integer> getLives() {
		return new TupleTwo<Integer>(
				level.getPlayers().get(0).getLives(),
				0
		);
	}

	@Override
	public TupleTwo<Integer> getScores() {
		return new TupleTwo<Integer>(
				level.getPlayers().get(0).getScore(),
				0
		);
	}

	@Override
	public void newLevel() {
		super.newLevel("customMode");
	}

	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/customhighscores.xml", "Custom Mode");
		hsmc.insertScore(getScores().t0, 1);
	}
}
