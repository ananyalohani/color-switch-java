import java.util.Date;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import java.time.*;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1;

    private Integer id;
    private String label;
    private String gameStateFile;
    private Date timestamp;
    private static Integer lastSavedGameId;
    private String score;

    SavedGame(GameState state, String label) {
        this.score = state.getScore().toString();
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getScore() {
        return this.score;
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
    private transient ArrayList<AnchorPane> savedGameCards;

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

    public void addGame(SavedGame savedGame) {
        // TODO create and position a new saved game card correspoding to the SavedGame object passed
        AnchorPane savedGameCard = (AnchorPane) Utils.loadObject(FXMLs.Scene.GAME_CARD);
        ObservableList<Node> children = savedGameCard.getChildren();
        ((Text) children.get(0)).setText(savedGame.getLabel());
        ((Text) children.get(1)).setText(LocalDate.now());
        ((Text) children.get(5)).setText(savedGame.getScore());
        savedGameCards.add(savedGameCard);
    }

    public SavedGamesScene(Stage stage) {
        this.stage = stage;
    }
}
