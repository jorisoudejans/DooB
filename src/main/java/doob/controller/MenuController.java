package doob.controller;

import doob.App;
import doob.controller.GameController.GameMode;
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
	 * Navigate to the highscore sub-menu.
	 */
	@FXML void showHighscores() {
		MenuController mc = App.loadScene("/fxml/Highscore.fxml").getController();
	}
	
	/**
	 * Navigate to the singleplayer highscores menu.
	 */
	@FXML
	public void showSinglePlayerHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/highscores.xml", GameMode.SINGLEPLAYER);
	}
	
	/**
	 * Navigate to the duel highscores menu.
	 */
	@FXML
	public void showDuelPlayerHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/duelhighscores.xml", GameMode.DUEL);
	}
	
	/**
	 * Navigate to the coop highscores menu.
	 */
	@FXML
	public void showCoopPlayerHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/coophighscores.xml", GameMode.COOP);
	}
	
	/**
	 * Navigate to the survival highscores menu.
	 */
	@FXML
	public void showSurvivalHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/survivalhighscores.xml", GameMode.SURVIVAL);
	}
	
	/**
	 * Navigate to the options menu.
	 */
	@FXML
	public void showOptions() {
		App.loadScene("/FXML/OptionsMenu.fxml");
	}

	/**
	 * navigate to Survivalgame.
	 */
	@FXML
	public void playSurvivalMode() {
		GameController gc = App.loadScene("/fxml/game.fxml").getController();
		gc.initSurvival();
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}	
}
