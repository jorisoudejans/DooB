package doob.controller;

import doob.App;
import javafx.fxml.FXML;

/**
 * Opening menu.
 */
public class MenuController {
	
	/**
	 * Navigate to singleplayergame.
	 */
	@FXML
	public void playSinglePlayer() {
		GameController gc = App.loadScene("/fxml/game.fxml").getController();
		gc.initSinglePlayer();
	}
	
	/**
	 * Navigate to duel mode multiplayer game.
	 */
	@FXML
	public void playDuelMode() {
		GameController gc = App.loadScene("/fxml/game.fxml").getController();
		gc.initDuelMode();
	}
	
	/**
	 * Navigate to coop mode multiplayer game.
	 */
	@FXML
	public void playCoopMode() {
		GameController gc = App.loadScene("/fxml/CoopGame.fxml").getController();
		gc.initCoopMode();
	}
	
	/**
	 * Navigate to the highscores menu.
	 */
	@FXML
	public void showHighscores() {
		App.loadScene("/FXML/HighscoreMenu.fxml");
	}
	
	/**
	 * Navigate to the options menu.
	 */
	@FXML
	public void showOptions() {
		App.loadScene("/FXML/OptionsMenu.fxml");
	}
	
}
