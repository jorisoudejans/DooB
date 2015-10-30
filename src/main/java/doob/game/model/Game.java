package doob.game.model;


import doob.App;
import doob.DLog;
import doob.controller.OptionsController;
import doob.game.GameUI;
import doob.model.Player;
import doob.model.level.Level;
import doob.model.level.LevelFactory;
import doob.util.TupleTwo;
import javafx.animation.AnimationTimer;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Controller for games.
 */
public abstract class Game implements Observer {

	private List<String> levelList;
	private int currentLevel;
	protected Level level;
	private AnimationTimer timer;
	private double progress;
	private boolean running;
	private static final int TIME_BONUS = 3;
	private static final double PROGRESS_PER_TICK = 0.01;

	private DLog dLog;

	private GameUI ui;

	/**
	 * Init normal game.
	 * @param levelPath path to file
	 */
	public void initNormalGame(String levelPath) {
		initGame(levelPath, false);
	}

	/**
	 * Init custom game.
	 * @param levelPath path to file
	 */
	public void initCustomGame(String levelPath) {
		initGame(levelPath, true);
	}

	/**
	 * Initialization of the game pane.
	 * 
	 * @param levelPath
	 *            The path of the levels to be read
	 * @param isCustom custom game or not
	 */
	public void initGame(String levelPath, boolean isCustom) {
		dLog = DLog.getInstance();
		levelList = new ArrayList<String>();
		readLevels(levelPath, isCustom);
		ui.setLevelLabel(currentLevel);
		// gameState = GameState.RUNNING;
		createTimer();
		newLevel();
		readOptions();
		running = true;

		dLog.setFile("DooB.log");
		dLog.info("GameUI started.", DLog.Type.STATE);
	}

	/**
	 * Set controller for this class.
	 * @param ui controller
	 */
	public void setUI(GameUI ui) {
		this.ui = ui;
	}

	/**
	 * Initialize method called by javafx.
	 */
	public abstract void initialize();

	/**
	 * Continues or pauses the game dependent on wheter it is running or not.
	 */
	public void pausePlay() {
		if (running) {
			level.stopTimer();
			running = false;
			ui.setPlayPauseButton("Play");
		} else {
			level.startTimer();
			running = true;
			ui.setPlayPauseButton("Pause");
		}
	}

	/**
	 * Initializes the timer which uses an animationtimer to handle game steps.
	 * The method handle defines what to do in each gamestep.
	 */
	public void createTimer() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				updateScore();
				updateProgressBar();
				updateLives();
			}
		};
		timer.start();
	}

	/**
	 * Navigate back to the menu.
	 */
	public void backToMenu() {
		level.stopTimer();
		App.loadScene("/FXML/Menu.fxml");
	}

	/**
	 * Reads all levels out of the levels file as a string and puts them in a
	 * list of strings with all levels.
	 *
	 * @param levelPath
	 *            The path to the levels that have to be read.
	 * @param isCustom whether they are custom
	 */
	public void readLevels(String levelPath, boolean isCustom) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();

			Document doc = dBuilder.parse(new File(levelPath));
			doc.getDocumentElement().normalize();

			NodeList levels = doc.getElementsByTagName("LevelName");
			for (int i = 0; i < levels.getLength(); i++) {
				Node n = levels.item(i);
				String custom;
				if (isCustom) {
					custom = "/Custom";
				} else {
					custom = "";
				}
				String s = "src/main/resources/level/" + custom + n.getTextContent();
				levelList.add(s);
			}
			currentLevel = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads all options from the options xml.
	 */
	public void readOptions() {
		for (int i = 0; i < level.getPlayers().size(); i++) {
			OptionsController oc = new OptionsController(
					"src/main/resources/Options/OptionsPlayer" + (i + 1)
							+ ".xml");
			oc.read();
			Player.ControlKeys keys = new Player.ControlKeys(oc.getLeft(), 
					oc.getRight(), oc.getShoot());
			level.getPlayers().get(i).setControlKeys(keys);
		}
	}

	/**
	 * Updates the timebar every gamestep. If there is no time left the game
	 * freezes and the level restarts.
	 */
	public void updateProgressBar() {
		double progress = level.getCurrentTime() / level.getTime();
		if (progress <= 0) {
			for (Player player : level.getPlayers()) {
				if (player.isAlive()) {
					player.die();
				}
			}
			update(null, Level.Event.LOST_LIFE);
			return;
		}
		ui.setProgress(progress);
	}

	/**
	 * Empty the progressbar after a level is completed and add points for the remaining time.
	 */
	public void emptyProgressBar() {
		progress = level.getCurrentTime() / level.getTime();
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				progress = progress - PROGRESS_PER_TICK;
				if (progress <= 0) {
					this.stop();
				} else {
					ui.setProgress(progress);
					for (Player p : level.getPlayers()) {
						p.incrScore(TIME_BONUS);
					}
					updateScore();
				}
			}
		}.start();
	}

	/**
	 * On update of level.
	 * @param o the level
	 * @param arg an object
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Level.Event) {
			Level.Event event = (Level.Event) arg;
			switch (event) {
				case ZERO_LIVES:
					//App.loadHighscoreScene(score, score2, gameMode);
					break;
				case ALL_BALLS_GONE:
					onAllBallsGone();
					break;
				case LOST_LIFE:
					newLevel();
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Handle gameStateChange all balls gone.
	 */
	public void onAllBallsGone() {
		emptyProgressBar();
		dLog.info("Level " + (currentLevel + 1) + " completed!",
				DLog.Type.STATE);
		timer.stop();
		level.freeze(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				if (currentLevel < levelList.size() - 1) {
					currentLevel++;
					ui.setLevelLabel(currentLevel);
					newLevel();
					timer.start();
				} else {
					// gameState = GameState.WON;
					dLog.info("GameUI won!", DLog.Type.STATE);
					loadHighscores();
				}
			}
		});
	}

	/**
	 * Updates the amount of lives every gamestep.
	 */
	public void updateLives() {
		ui.setLives(getLives());
	}

	/**
	 * Get amount of lives each player has. For single player,
	 * return 0 for second player.
	 * @return lives
	 */
	public abstract TupleTwo<Integer> getLives();
	
	/**
	 * Updates the score every gamestep.
	 */
	public void updateScore() {
		ui.setScores(getScores());
	}
	/**
	 * Get score of each player. For single player,
	 * return 0 for second player.
	 * @return scores
	 */
	public abstract TupleTwo<Integer> getScores();

	/**
	 * Construct new level.
	 */
	public abstract void newLevel();
	
	/**
	 * Resets the level depending on currentLevel. Only keeps amount of lives.
	 * @param type the level type
	 */
	public void newLevel(String type) {
		List<Player> players = null;
		if (level != null) {
			level.stopTimer();
			players = level.getPlayers();
		}
		// TODO Remove canvas here.
		level = new LevelFactory(levelList.get(currentLevel), ui.getCanvas(), type).build();
		level.addObserver(this);
		if (players != null) {
			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				int lives = p.getLives();
				int score = p.getScore();
				level.getPlayers().get(i).setLives(lives);
				level.getPlayers().get(i).setScore(score);
			}
		}
		readOptions();
	}
	
	/**
	 * Show the highscores.
	 */
	public abstract void loadHighscores();
}
