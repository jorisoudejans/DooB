package doob.model;

import java.util.ArrayList;

public class SinglePlayerGame extends Game{
	private Player player;

	public SinglePlayerGame(int score, int difficulty, 
			ArrayList<Level> levels, Player p1) {
		super(score, difficulty, levels);
		this.player = p1;
	}
}
