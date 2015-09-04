package doob.controller;

import doob.App;
import javafx.fxml.FXML;

/**
 * Opening menu.
 */
public class MenuController {
	
	@FXML
	public void playSinglePlayer() {
		App.loadScene("/fxml/game.fxml");
	}
	
}
