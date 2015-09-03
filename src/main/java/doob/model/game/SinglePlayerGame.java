package doob.model.game;

import doob.model.Level;
import doob.model.Player;

import java.util.ArrayList;

public class SinglePlayerGame extends AbstractGame {
	private Player player;

	public SinglePlayerGame(int score, int difficulty, 
			ArrayList<Level> levels, Player p1) {
		super(score, difficulty, levels);
		this.player = p1;
	}
}
