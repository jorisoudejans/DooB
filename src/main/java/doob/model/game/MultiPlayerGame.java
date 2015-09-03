package doob.model.game;

import doob.model.Level;
import doob.model.PlayerModel;

import java.util.ArrayList;

public class MultiPlayerGame extends AbstractGame {

	private PlayerModel player1;
	private PlayerModel player2;
	
	public MultiPlayerGame(int score, int difficulty, ArrayList<Level> levels, PlayerModel p1, PlayerModel p2) {
		super(score, difficulty, levels);
		this.player1 = p1;
		this.player2 = p2;
	}

}
