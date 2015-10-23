package doob.controller;

import doob.App;
import doob.levelBuilder.LevelBuilderController;
import javafx.fxml.FXML;
import doob.game.CoopGame;
import doob.game.DuelGame;
import doob.game.SinglePlayerGame;
import doob.game.SurvivalGame;

/**
 * Opening menu.
 */
public class MenuController {
	
	/**
	 * Navigate to singleplayergame.
	 */
	@FXML
	public void playSinglePlayer() {
		SinglePlayerGame spg = App.loadScene("/FXML/SinglePlayerGame.fxml").getController();
		spg.initialize();
	}
	
	/**
	 * Navigate to duel mode multiplayer game.
	 */
	@FXML
	public void playDuelMode() {
		DuelGame dg = App.loadScene("/FXML/DuelGame.fxml").getController();
		dg.initialize();
	}
	
	/**
	 * Navigate to coop mode multiplayer game.
	 */
	@FXML
	public void playCoopMode() {
		CoopGame cg = App.loadScene("/FXML/CoopGame.fxml").getController();
		cg.initialize();
	}
	
	/**
	 * navigate to Survivalgame.
	 */
	@FXML
	public void playSurvivalMode() {
		SurvivalGame sg = App.loadScene("/FXML/SurvivalGame.fxml").getController();
		sg.initialize();
	}
	
	/**
	 * Navigate to the highscore sub-menu.
	 */
	@FXML void showHighscores() {
		App.loadScene("/fxml/Highscore.fxml").getController();
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
	 * Play the custom created levels.
	 */
	@FXML
	public void playCustom() {
		SinglePlayerGame spg = App.loadScene("/FXML/SinglePlayerGame.fxml").getController();
		spg.initializeCustom();
	}
	
	/**
	 * Navigate to the levelbuilder.
	 */
	@FXML
	public void buildLevel() {
		App.loadScene("/fxml/levelbuilder.fxml");
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}	
}
