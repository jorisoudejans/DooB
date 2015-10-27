package doob.levelBuilder;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;

/**
 * Controller class for a popup in the levelbuilder.
 */
public class LevelBuilderPopupController {

	@FXML
	private Button popupOKButton;
	@FXML
	private TextField popupTextField;

	private ArrayList<Ball> ballList;
	private ArrayList<Wall> wallList;
	private ArrayList<Player> playerList;
	private Stage popup;
	private TextField timeField;

	/**
	 * Acts as a contstructor.
	 * @param popup The stage where the popup is shown on.
	 * @param timeField The textfield where the level time is entered.
	 * @param ballList The ball list.
	 * @param wallList The wall list.
	 * @param playerList The player list.
	 */
	public void initPopup(Stage popup, TextField timeField,
			ArrayList<Ball> ballList, ArrayList<Wall> wallList,
			ArrayList<Player> playerList) {
		this.popup = popup;
		this.timeField = timeField;
		this.ballList = ballList;
		this.wallList = wallList;
		this.playerList = playerList;
	}

	/**
	 * When the OK button is pressed in the popup the level is saved with a LevelWriter.
	 */
	@FXML
	public void saveName() {
		String name = popupTextField.getText();
		if (name.length() > 0) {
			try {
				new LevelWriter(ballList, wallList, playerList,
						Integer.parseInt(timeField.getText()), name)
						.saveToFXML();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			popup.close();
		}
	}

}
