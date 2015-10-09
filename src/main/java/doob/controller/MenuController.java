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
		App.loadScene("/fxml/game.fxml");
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
