package doob.game;


import doob.game.model.Game;
import doob.util.TupleTwo;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


/**
 * Controller for games.
 */
public class GameUI {

	@FXML
	private Canvas canvas;
	@FXML
	private Pane pane;
	@FXML
	private Canvas lives1;
	@FXML
	private Canvas lives2;
	@FXML
	private Label scoreTextView1;
	@FXML
	private Label scoreTextView2;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label levelLabel;
	@FXML
	private Button playPauseButton;

	private GraphicsContext gc;
	private GraphicsContext gc2;
	public static final int HEART_SPACE = 40;
	public static final int HEART_Y = 8;

	private Game game;

	/**
	 * Set game to be associated with this controller.
	 * @param game game
	 */
	public void setGame(Game game) {
		this.game = game;
		game.initialize();
	}

	/**
	 * Initialize graphics context etc.
	 */
	public void initialize() {
		gc = lives1.getGraphicsContext2D();
		gc2 = lives2.getGraphicsContext2D();
		Canvas background = new Canvas(canvas.getWidth(), canvas.getHeight());
		GraphicsContext gcBg = background.getGraphicsContext2D();
		gcBg.drawImage(new Image("/image/background.jpg"), 0, 0,
				canvas.getWidth(), canvas.getHeight());
		pane.getChildren().add(background);
		background.toBack();
	}

	/**
	 * Set the current level in the label.
	 * @param currentLevel current level
	 */
	public void setLevelLabel(int currentLevel) {
		levelLabel.setText((currentLevel + 1) + "");
	}

	/**
	 * Set text of the play pause button.
	 * @param text text
	 */
	public void setPlayPauseButton(String text) {
		playPauseButton.setText(text);
	}

	/**
	 * Set progressbar progress.
	 * @param progress progress
	 */
	public void setProgress(double progress) {
		progressBar.setProgress(progress);
	}

	/**
	 * Navigate back to the menu.
	 */
	@FXML
	public void backToMenu() {
		game.backToMenu();
	}

	/**
	 * Continues or pauses the game dependent on wheter it is running or not.
	 */
	@FXML
	public void pausePlay() {
		game.pausePlay();
	}

	/**
	 * Set lives to be displayed of the maximum of two players.
	 * @param lives lives of both players
	 */
	public void setLives(TupleTwo<Integer> lives) {
		gc.clearRect(0, 0, lives1.getWidth(), lives1.getHeight());

		for (int i = 0; i < lives.t0; i++) {
			gc.drawImage(new Image("/image/heart.png"), i
					* HEART_SPACE, HEART_Y);
		}
		for (int i = 0; i < lives.t1; i++) {
			gc2.drawImage(new Image("/image/heart.png"), i
					* HEART_SPACE, HEART_Y);
		}
	}

	/**
	 * Set scores to be displayed of the maximum of two players.
	 * @param scores scores of both players
	 */
	public void setScores(TupleTwo<Integer> scores) {
		scoreTextView1.setText(scores.t0.toString());
		scoreTextView2.setText(scores.t1.toString());
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
