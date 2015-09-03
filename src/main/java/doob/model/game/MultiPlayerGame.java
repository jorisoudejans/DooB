package doob.model.game;

import doob.model.Level;
import doob.model.Player;

import java.util.ArrayList;

public class MultiPlayerGame extends AbstractGame {

	private Player player1;
	private Player player2;
	
	public MultiPlayerGame(int score, int difficulty, ArrayList<Level> levels, Player p1, Player p2) {
		super(score, difficulty, levels);
		this.player1 = p1;
		this.player2 = p2;
	}

}
