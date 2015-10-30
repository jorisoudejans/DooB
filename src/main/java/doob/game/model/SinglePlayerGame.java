package doob.game.model;

import doob.App;
import doob.controller.HighscoreMenuController;
import doob.model.levelbuilder.LevelReader;
import doob.util.TupleTwo;

/**
 * Class to play a singleplayer game.
 */
public class SinglePlayerGame extends Game {

	/**
	 * Load the list of single-player levels.
	 */
	public void initialize() {
		initNormalGame("src/main/resources/Level/SinglePlayerLevels.xml");
	}
	
	/**
	 * Load the list of custom made levels.
	 */
	public void initializeCustom() {
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
		super.newLevel("single");
	}

	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/highscores.xml", "SinglePlayer Mode");
		hsmc.insertScore(getScores().t0, 1);
	}
}
