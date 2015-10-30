package doob.controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import doob.App;
import doob.model.Score;
import doob.popup.InputPopup;

/**
 * Class to control the highscores menu.
 */
public class HighscoreMenuController {

	@FXML
	private TableColumn<Score, String> nameCol;
	@FXML
	private TableColumn<Score, Integer> scoreCol;
	@FXML
	private TableView<Score> scoreTable;
	@FXML
	private Label gameModeLabel;
	
	private HighscoreController hsc;
	private String source;
	private String labelText;

	private static final int CELL_SIZE = 70;
	
	/**
	 * Read the highscores file and insert the scores into the table.
	 * @param source The path to the highscores file.
	 * @param labelText The gamemode shown above the highscores.
	 */
	public void updateTable(String source, String labelText) {
		this.source = source;
		this.labelText = labelText;
		gameModeLabel.setText(labelText);
		hsc = new HighscoreController(source);
		ArrayList<Score> scoreList = hsc.read();
		nameCol.setCellValueFactory(new PropertyValueFactory<Score, String>(
				"name"));
		scoreCol.setCellValueFactory(new PropertyValueFactory<Score, Integer>(
				"score"));
		ObservableList<Score> oscoreList = FXCollections
				.observableArrayList(scoreList);
		scoreTable.setItems(oscoreList);
		scoreTable.setFixedCellSize(CELL_SIZE);
	}
	
	/**
	 * Insert a new score into the table and the highscore file.
	 * @param score The score to be inserted.
	 * @param player The player for whom the popup is shown.
	 */
	public void insertScore(final int score, int player) {
		if (hsc.highScoreIndex(score) == -1) {
			return;	
		}
		final Stage dialog = new Stage();
		final InputPopup popup = App.popup(dialog,
				"/FXML/InputPopup.fxml").getController();
		String text = "Player " + player + " has a highscore! Enter your name:";
		popup.setText(text);
		popup.setOnOK(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = popup.getInput();
				if (name.length() > 0) {
					int index = hsc.highScoreIndex(score);
					dialog.close();
					hsc.addScore(new Score(name, score), index);
					hsc.write();
					updateTable(source, labelText);
					scoreTable.getSelectionModel().select(index);
				}
			}
		});
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}
	
	/**
	 * Navigate to the highscores menu.
	 */
	@FXML
	public void showHighScores() {
		App.loadScene("/FXML/HighScore.fxml");
	}

}
