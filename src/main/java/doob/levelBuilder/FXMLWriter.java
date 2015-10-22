package doob.levelBuilder;

import java.util.ArrayList;

import doob.model.Ball;
import doob.model.Player;
import doob.model.Wall;

/**
 * This class writes the created level to a suitable FXML file.
 *
 */
public class FXMLWriter {
	
	private ArrayList<Wall> walls;
	private ArrayList<Ball> balls;
	private ArrayList<Player> players;
	
	/**
	 * Basic constructor.
	 * @param ballList the balls to be added to the level
	 * @param wallList the walls to be added to the level
	 * @param playerList the players to be added to the level
	 */
	public FXMLWriter(ArrayList<Ball> ballList, ArrayList<Wall> wallList, 
			ArrayList<Player> playerList) {
		this.walls = wallList;
		this.balls = ballList;
		this.players = playerList;
	}

}
