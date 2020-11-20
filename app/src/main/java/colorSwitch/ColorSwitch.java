import java.io.Serializable;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import java.lang.Exception;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

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
        // startMenu.displayMenu();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Color Switch");
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scenes/startMenu.fxml"));
            stage.setScene(new Scene(root));
        } catch(Exception e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
