import java.io.Serializable;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class ColorSwitch extends Application implements Serializable {
    private transient StartMenu startMenu;
    private ArrayList<SavedGame> savedGames;
    private Integer totalScore;
    
    public ColorSwitch() {
        startMenu = new StartMenu(this);
    }

    public SavedGame getSavedGames(Integer id) {
        return null;
    }

    public void _init() {
        launch();
    }

    // the class that extends Application must have a default constructor
    @Override
    public void start(Stage stage) {
        stage.setTitle("Color Switch");
        startMenu.setStage(stage);
        startMenu.displayMenu();
        stage.show();
    }
}
