package doob.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
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
		App.getStage().getScene().getOnKeyPressed();
		//TODO
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}
	
	/**
	 * Set the selected key to LEFT.
	 */
	@FXML
	public void selectLeftKey() {
		key = Key.LEFT;
	}
	
	/**
	 * Set the selected key to RIGHT.
	 */
	@FXML
	public void selectRightKey() {
		key = Key.RIGHT;
	}
	
	/**
	 * Set the selected key to SHOOT.
	 */
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
	
	/**
	 *	Adapter to handle keys pressed .
	 */
	public class ControlAdapter implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
				switch (key) {
					case LEFT: //TODO set event.getCode to be the leftkey
					case RIGHT: //TODO set event.getCode to be the rightkey
					case SHOOT: //TODO set event.getCode to be the shootkey
					case NONE: break;
					default: break;
				}
			}
	}
	
}
