import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import java.io.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import javafx.fxml.FXML;

public class SavedGamesController {
    private SavedGamesScene savedGamesScene;

    @FXML
    private Button resumeBtn, deleteBtn;

    @FXML
    private Group backBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {}

    @FXML
    private void backBtnClicked() {
        savedGamesScene.goBack();
    }

    public void setup(SavedGamesScene savedGamesScene) {
        this.savedGamesScene = savedGamesScene;
        savedGamesScene.setScrollPane(scrollPane);
    }

    public SavedGamesController() {}
}
