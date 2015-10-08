package doob.controller;

import javafx.fxml.FXML;
import doob.App;

/**
 * Controller class for the options menu.
 */
public class OptionsMenuController {
	
	private Key key;

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		key = Key.NONE;
		//TODO
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}
	
	@FXML
	public void selectLeftKey() {
		key = Key.LEFT;
	}
	
	@FXML
	public void selectRightKey() {
		key = Key.RIGHT;
	}
	
	@FXML
	public void selectShootKey() {
		key = Key.SHOOT;
	}
	
	/**
	 * Key defines which button is selected to adjust with a new key.
	 */
	public enum Key {
		LEFT,
		RIGHT,
		SHOOT,
		NONE
	}
}
