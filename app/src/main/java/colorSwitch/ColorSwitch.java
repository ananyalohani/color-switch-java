import java.io.Serializable;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

public class ColorSwitch extends Application implements Serializable {
    private transient StartMenu startMenu;
    private ArrayList<SavedGame> savedGames;
    private Integer totalScore;

    public SavedGame getSavedGames(Integer id) {

    }

    public void init() {
        launch();
        startMenu.displayMenu();
    }

    @Override
    public void start(Stage primaryStage) {

    }

    ColorSwitch() {
        startMenu = new StartMenu(this);
    }
}
