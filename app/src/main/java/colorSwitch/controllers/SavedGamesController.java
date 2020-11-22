import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import javafx.fxml.FXML;

public class SavedGamesController {
    // private SavedGame savedGame;
    private Stage stage;

    @FXML
    private Button resumeBtn, deleteBtn;

    @FXML
    private Group backBtn;

    @FXML
    private void initialize() {}

    @FXML
    private void backBtnClicked() {
        StartMenu sm = new StartMenu(null);
        sm.setStage(stage);
        sm.displayMenu();
    }

    public SavedGamesController() {}

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
