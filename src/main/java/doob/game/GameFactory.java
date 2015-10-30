package doob.game;

import doob.App;
import doob.game.model.*;

/**
 * Factory class for games.
 */
public class GameFactory {

    private static final String PATH_SINGLE_PLAYER = "/FXML/SinglePlayerGame.fxml";
    private static final String PATH_DUEL_MODE = "/FXML/DuelGame.fxml";
    private static final String PATH_COOP_MODE = "/FXML/CoopGame.fxml";
    private static final String PATH_SURVIVAL_MODE = "/FXML/SurvivalGame.fxml";

    /**
     * Get specified game. Default is single player game.
     * @param type type of the game
     * @return game
     */
    // Suppress because we know the paths are good, so no null pointers here.
    @SuppressWarnings("ConstantConditions")
    public GameUI getGame(String type) {
        String path = PATH_SINGLE_PLAYER;
        Game game = new SinglePlayerGame();
        if (type.equals("duelMode")) {
            game = new DuelGame();
            path = PATH_DUEL_MODE;
        }
        if (type.equals("coopMode")) {
            game = new CoopGame();
            path = PATH_COOP_MODE;
        }
        if (type.equals("survivalMode")) {
            game = new SurvivalGame();
            path = PATH_SURVIVAL_MODE;
        }
        if (type.equals("customMode")) {
        	game = new CustomGame();
            path = PATH_SINGLE_PLAYER;
        }
        GameUI controller = App.loadScene(path).getController();
        game.setUI(controller);
        controller.setGame(game);
        return controller;
    }

}
