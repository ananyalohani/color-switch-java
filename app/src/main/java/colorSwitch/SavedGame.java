import java.util.Date;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;

public class SavedGame implements Serializable {
    private Integer id;
    private String label;
    private String gameStateFile;
    private Date timestamp;
    private static Integer lastSavedGameId;

    SavedGame(GameState state, String label) {
        
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getGameStateFile() {
        return this.gameStateFile;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}

class SavedGamesScene implements IScene {
    private Stage stage;

    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLs.Scene.SAVED_GAMES));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            SavedGamesController sgController = loader.getController();
            sgController.setup(this);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        StartMenu startMenu = new StartMenu(null);
        startMenu.setStage(stage);
        startMenu.display();
    }

    public SavedGamesScene(Stage stage) {
        this.stage = stage;
    }
}
