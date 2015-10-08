package doob.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import doob.App;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class for the options menu.
 */
public class OptionsMenuController {

	@FXML
	private AnchorPane anchorPane;

	private Key key;

	private KeyCode leftKey;
	private KeyCode rightKey;
	private KeyCode shootKey;

	private OptionsController oc;

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		key = Key.NONE;
		anchorPane.setOnKeyPressed(new ControlAdapter());

		oc = new OptionsController("src/main/resources/Options/Options.xml");
		oc.read();
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		write();
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
					case LEFT:
						if(rightKey != event.getCode() && shootKey != event.getCode()){
							leftKey = event.getCode();
						}
					case RIGHT:
						if(leftKey != event.getCode() && shootKey != event.getCode()){
							rightKey = event.getCode();
						}
					case SHOOT:
						if(rightKey != event.getCode() && leftKey != event.getCode()){
							shootKey = event.getCode();
						}
					case NONE: break;
					default: break;
				}
			}
	}

	public void write(){
		if(leftKey == null &&
				rightKey == null &&
				shootKey == null) return;
		if(leftKey != null){
			oc.setLeft(leftKey);
		}
		if(rightKey != null){
			oc.setRight(rightKey);
		}
		if(shootKey != null){
			oc.setShoot(shootKey);
		}
		oc.write();

	}
	
}
