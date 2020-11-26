import java.io.Serializable;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import javafx.scene.*;
import javafx.stage.*;

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

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/startMenu.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch(IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Color Switch");
        stage.show();

        startMenu.setStage(stage);
        startMenu.display();
    }
}
