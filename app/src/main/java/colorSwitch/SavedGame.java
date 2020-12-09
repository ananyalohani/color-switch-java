import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import java.time.*;
import java.util.*;
import javafx.scene.layout.AnchorPane;
import javafx.collections.*;
import java.time.format.DateTimeFormatter;
import javafx.scene.text.Text;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1;

    // private Integer id;
    private String label;
    private String gameStateFile;
    private String timestamp;
    // private static Integer lastSavedGameId;
    private String score;

    SavedGame(GameState state) {
        this.score = state.getScore().toString();
        this.label = "SAVED GAME " + App.game.getStats().getStat(Stat.SAVED_COUNT);
        this.timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/mm/yyyy HH:MM"));
        this.gameStateFile = Constants.DataFiles.ROOT + label;
        App.game.getStats().setStat(Stat.SAVED_COUNT, 1, true);
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

    public String getTimestamp() {
        return this.timestamp;
    }
}

class SavedGamesScene implements IScene {
    private StartMenu startMenu;
    private transient ArrayList<AnchorPane> savedGameCards;

    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Scene.SAVED_GAMES));

            Scene scene = new Scene(loader.load());
            startMenu.getStage().setScene(scene);

            SavedGamesController sgController = loader.getController();
            sgController.setup(this);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        startMenu.display();
    }

    public void addGame(SavedGame savedGame) {
        // TODO create and position a new saved game card correspoding to the SavedGame object passed
        AnchorPane savedGameCard = (AnchorPane) Utils.loadObject(Constants.Scene.GAME_CARD);
        ObservableList<Node> children = savedGameCard.getChildren();
        ((Text) children.get(0)).setText(savedGame.getLabel());
        ((Text) children.get(1)).setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/mm/yyyy")));
        ((Text) children.get(5)).setText(savedGame.getScore());
        savedGameCards.add(savedGameCard);
    }

    public SavedGamesScene(StartMenu startMenu) {
        this.startMenu = startMenu;
    }
}
