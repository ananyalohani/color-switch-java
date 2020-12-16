public class Constants {
    class Scene {
        public static final String START_MENU = "scenes/startMenu.fxml";
        public static final String PAUSE_MENU = "scenes/pauseMenu.fxml";
        public static final String END_MENU = "scenes/endMenu.fxml";
        public static final String GAMEPLAY = "scenes/gameplay.fxml";
        public static final String LEADERBOARD = "scenes/leaderboard.fxml";
        public static final String SAVED_GAMES = "scenes/savedGames.fxml";
        public static final String GAME_CARD = "scenes/savedGameCard.fxml";
        public static final String SETTINGS = "scenes/settings.fxml";
        public static final String HELP = "scenes/help.fxml";
        public static final String RESTART_DIALOG = "scenes/restartGameDialog.fxml";
        public static final String STATISTICS = "scenes/statistics.fxml";
    }

    class Obstacle {
        public static final String CIRCLE = "game-objects/circleObstacle.fxml";
        public static final String GEARS = "game-objects/gearsObstacle.fxml";
        public static final String SQUARE = "game-objects/squareObstacle.fxml";
        public static final String BAR = "game-objects/barObstacle.fxml";
    }

    class DataFiles {
        public static final String ROOT = "src/main/data/";
        public static final String COLOR_SWITCH = ROOT + "ColorSwitch";
    }
}
