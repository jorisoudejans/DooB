package doob;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import doob.controller.GameController.GameMode;
import doob.controller.HighscoreMenuController;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App extends Application {
	private static Stage stage;
	private static AnchorPane pane;

	@Override
	public void start(Stage stageIn) {
		stage = stageIn;
		stage.setTitle("DooB");
		loadScene("/FXML/Menu.fxml");
	}

	/**
	 * Loads the given scene.
	 * 
	 * @param path
	 *            Location of the fxml file
	 * @return The FXMLLoader.
	 */
	public static FXMLLoader loadScene(String path) {
		try {
			// Load the anchor pane
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource(path));
			pane = loader.load();

			// Set the pane onto the scene
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
			System.out.println(path + " loaded on the stage");
			return loader;
		} catch (IOException e) {
			e.printStackTrace();
			System.out
					.println("Something went wrong while loading the fxml file");
		}
		return null;
	}

	/**
	 * Loads the highscore scene and takes the new score as a parameter.
	 * 
	 * @param score
	 *            The score that is passed as a parameter to the controller
	 * @param score2 The score of the second player
	 * @param gameMode The gameMode for which the highscores should be loaded
	 */
	public static void loadHighscoreScene(int score, int score2, GameMode gameMode) {
		HighscoreMenuController hsmc = loadScene("/FXML/HighscoreMenu.fxml").getController();
		
		switch (gameMode) {
		case SINGLEPLAYER:
			hsmc.updateTable("src/main/resources/Highscore/highscores.xml", gameMode);
			break;
		case DUEL:
			hsmc.updateTable("src/main/resources/Highscore/duelhighscores.xml", gameMode);
			hsmc.insertScore(score2, 2);
			break;
		case COOP:
			hsmc.updateTable("src/main/resources/Highscore/coophighscores.xml", gameMode);
			break;
		case SURVIVAL:
			hsmc.updateTable("src/main/resources/Highscore/survivalhighscores.xml", gameMode);
			break;
		default:
			break;
		}
		
		hsmc.insertScore(score, 1);
	}

	public static Stage getStage() {
		return stage;
	}

	/**
	 * Starts the game.
	 * 
	 * @param args
	 *            param
	 * @throws IOException
	 *             exception
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Working Directory = "
				+ System.getProperty("user.dir"));
		launch(args);
	}
}
