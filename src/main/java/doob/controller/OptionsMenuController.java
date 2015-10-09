package doob.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import doob.App;

/**
 * Controller class for the options menu.
 */
public class OptionsMenuController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button leftButton;

	@FXML
	private Button rightButton;

	@FXML
	private Button shootButton;

	private Key selectedKey;

	private KeyCode leftKey;
	private KeyCode rightKey;
	private KeyCode shootKey;

	private OptionsController oc;

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		selectedKey = Key.NONE;
		anchorPane.setOnKeyPressed(new ControlAdapter());

		oc = new OptionsController("src/main/resources/Options/Options.xml");
		oc.read();

		leftButton.setText(oc.getLeft().getName());
		rightButton.setText(oc.getRight().getName());
		shootButton.setText(oc.getShoot().getName());

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
	 * Update the display of the buttons to the new values.
	 */
	public void update() {
		if (leftKey != null) {
			leftButton.setText(leftKey.getName());
		}
		if (rightKey != null) {
			rightButton.setText(rightKey.getName());
		}
		if (shootKey != null) {
			shootButton.setText(shootKey.getName());
		}
	}

	/**
	 * Set the selected key to LEFT.
	 */
	@FXML
	public void selectLeftKey() {
		selectedKey = Key.LEFT;
	}

	/**
	 * Set the selected key to RIGHT.
	 */
	@FXML
	public void selectRightKey() {
		selectedKey = Key.RIGHT;
	}

	/**
	 * Set the selected key to SHOOT.
	 */
	@FXML
	public void selectShootKey() {
		selectedKey = Key.SHOOT;
	}

	/**
	 * Key defines which button is selected to adjust with a new key.
	 */
	public enum Key {
		LEFT, RIGHT, SHOOT, NONE
	}

	/**
	 * Adapter to handle keys pressed .
	 */
	public class ControlAdapter implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			switch (selectedKey) {
			case LEFT:
				if (rightKey != event.getCode() && shootKey != event.getCode()) {
					leftKey = event.getCode();
					update();
				}
				break;
			case RIGHT:
				if (leftKey != event.getCode() && shootKey != event.getCode()) {
					rightKey = event.getCode();
					update();
				}
				break;
			case SHOOT:
				if (rightKey != event.getCode() && leftKey != event.getCode()) {
					shootKey = event.getCode();
					update();
				}
				break;
			case NONE:
				break;
			default:
				break;
			}
			selectedKey = Key.NONE;
		}
	}

	/**
	 * Write the keysettings to the optionscontroller.
	 */
	public void write() {
		if (leftKey == null && rightKey == null && shootKey == null) {
			return;
		}
		if (leftKey != null) {
			oc.setLeft(leftKey);
		}
		if (rightKey != null) {
			oc.setRight(rightKey);
		}
		if (shootKey != null) {
			oc.setShoot(shootKey);
		}
		oc.write();

	}

}
