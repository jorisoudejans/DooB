package doob.popup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller class for a popup in the levelbuilder.
 */
public class DisplayPopup {

	@FXML
	private Label text;
	@FXML
	private Button popupOKButton;
	
	/**
	 * Set the text of the popup.
	 * @param text The text to be shown when popped up.
	 */
	public void setText(String text) {
		this.text.setText(text);
	}

	/**
	 * Set the default eventhanlder when the OK button is pressed, i.e. close the popup.
	 * @param stage The stage to be closed.
	 */
	public void setDefaultOnOK(final Stage stage) {
		popupOKButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
	}
	
	/**
	 * Set the eventhandler for when the OK button is pressed.
	 * @param handler The eventhandler for the OK button.
	 */
	public void setOnOK(EventHandler<ActionEvent> handler) {
		popupOKButton.setOnAction(handler);
	}

}
