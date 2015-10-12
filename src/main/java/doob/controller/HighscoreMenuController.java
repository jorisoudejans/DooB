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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import doob.App;
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

	private HighscoreController hsc;

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		updateTable();
	}
	
	/**
	 * Read the highscores file and insert the scores into the table.
	 */
	public void updateTable() {
		hsc = new HighscoreController(
				"src/main/resources/Highscore/highscores.xml");
		ArrayList<Score> scoreList = hsc.read();
		nameCol.setCellValueFactory(new PropertyValueFactory<Score, String>(
				"name"));
		scoreCol.setCellValueFactory(new PropertyValueFactory<Score, Integer>(
				"score"));
		ObservableList<Score> oscoreList = FXCollections
				.observableArrayList(scoreList);
		scoreTable.setItems(oscoreList);
		scoreTable.setFixedCellSize(70);
	}
	
	/**
	 * Insert a new score into the table and the highscore file.
	 * @param score The score to be inserted.
	 */
	public void insertScore(final int score) {
		if (hsc.highScoreIndex(score) == -1) {
			return;
		}
		final Stage dialog = new Stage();
		dialog.initOwner(App.getStage());
		
		Label l = new Label("You got a highscore! Enter your name");
		l.setFont(new Font(22));
		final TextField tf = new TextField();
		tf.setMaxWidth(350);
		tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER) {
					handleAction(dialog, tf, score);
				}
			}
		});
		
		Button b = new Button("OK");
		b.setPrefWidth(100);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleAction(dialog, tf, score);
			}
		});
		
		VBox popUpVBox = new VBox(10);
		popUpVBox.setAlignment(Pos.CENTER);
        popUpVBox.getChildren().add(l);
        popUpVBox.getChildren().add(tf);
        popUpVBox.getChildren().add(b);

        Scene dialogScene = new Scene(popUpVBox, 400, 150);
		dialog.setScene(dialogScene);
		dialog.show();
	}
	
	private void handleAction(Stage dialog, TextField tf, int score) {
		String name = tf.getText();
		if (name.length() > 0) {
			int index = hsc.highScoreIndex(score);
			dialog.close();
			hsc.addScore(new Score(name, score), index);
			hsc.write();
			updateTable();
			scoreTable.getSelectionModel().select(index);
		}
	}
	
	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		App.loadScene("/FXML/Menu.fxml");
	}

}
