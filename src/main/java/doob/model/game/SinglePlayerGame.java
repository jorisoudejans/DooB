package doob.model.game;

import doob.model.Level;
import doob.model.PlayerModel;

import java.util.ArrayList;

public class SinglePlayerGame extends AbstractGame {
	private PlayerModel player;

	public SinglePlayerGame(int score, int difficulty, 
			ArrayList<Level> levels, PlayerModel p1) {
		super(score, difficulty, levels);
		this.player = p1;
	}
}
