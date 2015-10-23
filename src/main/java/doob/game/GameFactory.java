package doob.game;

import doob.App;

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
    public Game getGame(String type) {
        if (type == null) {
            return App.loadScene(PATH_SINGLE_PLAYER).getController();
        }
        if (type.equals("duelMode")) {
            return App.loadScene(PATH_DUEL_MODE).getController();
        }
        if (type.equals("coopMode")) {
            return App.loadScene(PATH_COOP_MODE).getController();
        }
        if (type.equals("survivalMode")) {
            return App.loadScene(PATH_SURVIVAL_MODE).getController();
        }
        return App.loadScene(PATH_SINGLE_PLAYER).getController();
    }

}
