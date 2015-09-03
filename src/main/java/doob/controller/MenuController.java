package doob.controller;

import doob.App;
import javafx.fxml.FXML;

public class MenuController {
	
	@FXML
	public void playSinglePlayer() {
		App.loadScene("/FXML/Game.fxml");
	}
	
}
