package doob.game.model;

import doob.App;
import doob.controller.HighscoreMenuController;
import doob.model.powerup.LifePowerUp;
import doob.model.powerup.TimePowerUp;
import doob.util.TupleTwo;

/**
 * Class to play a singleplayer game.
 */
public class SurvivalGame extends Game {

	public void initialize() {
		initNormalGame("src/main/resources/Level/SurvivalLevels.xml");
		level.getPlayers().get(0).setLives(1);
		level.getPowerUpManager().getAvailablePowerups().remove(LifePowerUp.class);
		level.getPowerUpManager().getAvailablePowerups().remove(TimePowerUp.class);
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
		super.newLevel("survival");
	}

	@Override
	public void loadHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/survivalhighscores.xml", "Surival Mode");
		hsmc.insertScore(getScores().t0, 1);
	}
}
