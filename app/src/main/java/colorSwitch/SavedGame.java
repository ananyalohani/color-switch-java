import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1;

    // private Integer id;
    private String label;
    private String gameStateFile;
    private String timestamp;
    // private static Integer lastSavedGameId;
    private String score;
    private GameState state;

    SavedGame(GameState state) {
        this.state = state;
        this.score = state.getScore().toString();
        this.label = "SAVED GAME " + App.game.getStats().getStat(Stat.SAVED_COUNT);
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.gameStateFile = Constants.DataFiles.ROOT + label;
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

    public GameState getState() {
        return this.state;
    }
}

class SavedGamesScene implements IScene {
    private StartMenu startMenu;
    private transient ArrayList<AnchorPane> savedGameCards;
    private double lastCardY;
    private ScrollPane scrollPane;
    private VBox vbox;

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

        for (int i = 0; i < App.game.getSavedGames().size(); i++) {
            addGame(App.game.getSavedGames().get(i), i);
        }
    }

    public void goBack() {
        startMenu.display();
    }

    public void addGame(SavedGame savedGame, int index) {
        // TODO create and position a new saved game card correspoding to the SavedGame object passed
        AnchorPane savedGameCard = (AnchorPane) Utils.loadObject(Constants.Scene.GAME_CARD);

        ObservableList<Node> children = savedGameCard.getChildren();
        ((Text) children.get(0)).setText(savedGame.getLabel());
        ((Text) children.get(1)).setText(savedGame.getTimestamp());
        ((Text) children.get(5)).setText(savedGame.getScore());

        Button resumeBtn = (Button) children.get(2);
        Button deleteBtn = (Button) children.get(3);

        resumeBtn.setOnAction(e -> {
            Gameplay savedGameGameplay = new Gameplay(savedGame, startMenu.getStage());
            savedGameGameplay.display();
            savedGameGameplay.playGame(true);
        });

        deleteBtn.setOnAction(e -> {
            App.game.getSavedGames().remove(index);
            display();
        });

        vbox.getChildren().add(savedGameCard);
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        vbox = (VBox) scrollPane.getContent();
    }

    public SavedGamesScene(StartMenu startMenu) {
        this.startMenu = startMenu;
    }
}
