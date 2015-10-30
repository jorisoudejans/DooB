package doob.popup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller class for a popup in the levelbuilder.
 */
public class InputPopup {

	@FXML
	private Label text;
	@FXML
	private Button popupOKButton;
	@FXML
	private TextField popupTextField;
	
	/**
	 * Set the text of the popup.
	 * @param text The text to be shown when popped up.
	 */
	public void setText(String text) {
		this.text.setText(text);
	}
	
	/**
	 * Set the eventhandler for when the OK button is pressed.
	 * @param handler The eventhandler for the OK button.
	 */
	public void setOnOK(EventHandler<ActionEvent> handler) {
		popupOKButton.setOnAction(handler);
		popupTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					popupOKButton.fire();
				}
			}
		});
	}
	
	/**
	 * Return the input of the text field.
	 * @return TextField input.
	 */
	public String getInput() {
		return popupTextField.getText();
	}

}
