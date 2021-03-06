package doob.controller;

import doob.App;
import doob.game.GameFactory;
import javafx.fxml.FXML;

/**
 * Opening menu.
 */
public class MenuController {

	private GameFactory gameFactory;

	public void initialize() {
		gameFactory = new GameFactory();
	}
	
	/**
	 * Navigate to singleplayergame.
	 */
	@FXML
	public void playSinglePlayer() {
		gameFactory.getGame("singlePlayer");
	}
	
	/**
	 * Navigate to duel mode multiplayer game.
	 */
	@FXML
	public void playDuelMode() {
		gameFactory.getGame("duelMode");
	}
	
	/**
	 * Navigate to coop mode multiplayer game.
	 */
	@FXML
	public void playCoopMode() {
		gameFactory.getGame("coopMode");
	}
	
	/**
	 * navigate to Survivalgame.
	 */
	@FXML
	public void playSurvivalMode() {
		gameFactory.getGame("survivalMode");
	}

	/**
	 * Play the custom created levels.
	 */
	@FXML
	public void playCustom() {
		gameFactory.getGame("customMode");
	}
	
	/**
	 * Navigate to the highscore sub-menu.
	 */
	@FXML void showHighscores() {
		App.loadScene("/FXML/Highscore.fxml");
	}
	
	/**
	 * Navigate to the singleplayer highscores menu.
	 */
	@FXML
	public void showSinglePlayerHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/highscores.xml", "SinglePlayer Mode");
	}
	
	/**
	 * Navigate to the duel highscores menu.
	 */
	@FXML
	public void showDuelPlayerHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/duelhighscores.xml", "Duel Mode");
	}
	
	/**
	 * Navigate to the coop highscores menu.
	 */
	@FXML
	public void showCoopPlayerHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/coophighscores.xml", "Coop Mode");
	}
	
	/**
	 * Navigate to the survival highscores menu.
	 */
	@FXML
	public void showSurvivalHighscores() {
		HighscoreMenuController hsmc = App.loadScene("/FXML/HighscoreMenu.fxml").getController();
		hsmc.updateTable("src/main/resources/Highscore/survivalhighscores.xml", "Survival Mode");
	}
	
	/**
	 * Navigate to the options menu.
	 */
	@FXML
	public void showOptions() {
		App.loadScene("/FXML/OptionsMenu.fxml");
	}
	
	/**
	 * Navigate to the levelbuilder.
	 */
	@FXML
	public void buildLevel() {
		App.loadScene("/FXML/levelbuilder.fxml");
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}	
}
