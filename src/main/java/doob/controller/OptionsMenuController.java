package doob.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
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
	@FXML
	private Slider volumeBar;
	private double volume;
	@FXML
	private Button leftButtonMP;
	@FXML
	private Button rightButtonMP;
	@FXML
	private Button shootButtonMP;
	
	private Key selectedKey;

	private KeyCode leftKey;
	private KeyCode rightKey;
	private KeyCode shootKey;
	
	private KeyCode leftKeyMP;
	private KeyCode rightKeyMP;
	private KeyCode shootKeyMP;

	private OptionsController oc;
	private OptionsController ocMP;

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		volume = -1;
		selectedKey = Key.NONE;
		anchorPane.setOnKeyPressed(new ControlAdapter());

		oc = new OptionsController("src/main/resources/Options/OptionsPlayer1.xml");
		oc.read();
		
		ocMP = new OptionsController("src/main/resources/Options/OptionsPlayer2.xml");
		ocMP.read();

		leftButton.setText(oc.getLeft().getName());
		rightButton.setText(oc.getRight().getName());
		shootButton.setText(oc.getShoot().getName());
		volumeBar.setValue(oc.getVolume());
		
		leftButtonMP.setText(ocMP.getLeft().getName());
		rightButtonMP.setText(ocMP.getRight().getName());
		shootButtonMP.setText(ocMP.getShoot().getName());

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
	 * Set the volume depending on the value of the volume scrollbar.
	 */
	@FXML
	public void setVolume() {
		volume = volumeBar.getValue();
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
		if (leftKeyMP != null) {
			leftButtonMP.setText(leftKeyMP.getName());
		}
		if (rightKeyMP != null) {
			rightButtonMP.setText(rightKeyMP.getName());
		}
		if (shootKeyMP != null) {
			shootButtonMP.setText(shootKeyMP.getName());
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
	 * Set the selected key to LEFT.
	 */
	@FXML
	public void selectLeftKeyMP() {
		selectedKey = Key.LEFTMP;
	}

	/**
	 * Set the selected key to RIGHT.
	 */
	@FXML
	public void selectRightKeyMP() {
		selectedKey = Key.RIGHTMP;
	}

	/**
	 * Set the selected key to SHOOT.
	 */
	@FXML
	public void selectShootKeyMP() {
		selectedKey = Key.SHOOTMP;
	}

	/**
	 * Key defines which button is selected to adjust with a new key.
	 * MP stands for multiplayer and defines the keys for player2.
	 */
	public enum Key {
		LEFT, RIGHT, SHOOT, LEFTMP, RIGHTMP, SHOOTMP, NONE
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
					update(); }
				break;
			case RIGHT:
				if (leftKey != event.getCode() && shootKey != event.getCode()) {
					rightKey = event.getCode();
					update(); }
				break;
			case SHOOT:
				if (rightKey != event.getCode() && leftKey != event.getCode()) {
					shootKey = event.getCode();
					update(); }
				break;
			case LEFTMP:
				if (rightKeyMP != event.getCode() && shootKeyMP != event.getCode()) {
					leftKeyMP = event.getCode();
					update(); }
				break;
			case RIGHTMP:
				if (leftKeyMP != event.getCode() && shootKeyMP != event.getCode()) {
					rightKeyMP = event.getCode();
					update(); }
				break;
			case SHOOTMP:
				if (rightKeyMP != event.getCode() && leftKeyMP != event.getCode()) {
					shootKeyMP = event.getCode();
					update(); }
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
		if (leftKey != null) {
			oc.setLeft(leftKey);
		}
		if (rightKey != null) {
			oc.setRight(rightKey);
		}
		if (shootKey != null) {
			oc.setShoot(shootKey);
		}
		if (volume != -1) {
			oc.setVolume(volume);
		}
		if (leftKeyMP != null) {
			ocMP.setLeft(leftKeyMP);
		}
		if (rightKeyMP != null) {
			ocMP.setRight(rightKeyMP);
		}
		if (shootKeyMP != null) {
			ocMP.setShoot(shootKeyMP);
		}

		oc.write();
		ocMP.write();

	}

}
