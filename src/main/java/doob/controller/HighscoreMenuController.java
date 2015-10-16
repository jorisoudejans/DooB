package doob.controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import doob.App;
import doob.controller.GameController.GameMode;
import doob.model.Score;

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
	private GameMode gameMode;
	
	private static final int BUTTON_WIDTH = 100;
	private static final int TEXT_FIELD_WIDTH = 350;
	private static final int CELL_SIZE = 70;
	
	/**
	 * Read the highscores file and insert the scores into the table.
	 * @param source The path to the highscores file.
	 * @param gameMode The gamemode which is chosen.
	 */
	public void updateTable(String source, GameMode gameMode) {
		this.source = source;
		this.gameMode = gameMode;
		gameModeLabel.setText(gameMode.getName());
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
			return;	}
		final Stage dialog = new Stage();
		dialog.initOwner(App.getStage());		
		Label l;
		if (gameMode == GameMode.DUEL) {
			l = new Label("Player " + player + " has a highscore! Enter your name");
		} else {
			l = new Label("You got a highscore! Enter your name");	}
		l.setFont(new Font(22));
		final TextField tf = new TextField();
		tf.setMaxWidth(TEXT_FIELD_WIDTH);		
		Button b = new Button("OK");
		b.setPrefWidth(BUTTON_WIDTH);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String name = tf.getText();
				if (name.length() > 0) {
					int index = hsc.highScoreIndex(score);
					dialog.close();
					hsc.addScore(new Score(name, score), index);
					hsc.write();
					updateTable(source, gameMode);
					scoreTable.getSelectionModel().select(index);
				}
			}
		});
		
		VBox popUpVBox = new VBox(10);
		popUpVBox.setAlignment(Pos.CENTER);
        popUpVBox.getChildren().add(l);
        popUpVBox.getChildren().add(tf);
        popUpVBox.getChildren().add(b);

        Scene dialogScene = new Scene(popUpVBox, 500, 150);
		dialog.setScene(dialogScene);
		dialog.show();
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
