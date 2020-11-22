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

public class EndMenuController {
    private EndMenu endMenu;
    private Stage stage;

    @FXML
    private Text scoreCount;

    @FXML
    private Button mainMenuBtn, saveToLBBtn, newGameBtn;

    public EndMenuController() {}

    @FXML
    private void initialize() {}

    @FXML
    private void mainMenuBtnClicked() {
        stage = (Stage) mainMenuBtn.getScene().getWindow();
        StartMenu sm = new StartMenu(null);
        sm.setStage(stage);
        sm.displayMenu();
    }

    @FXML
    private void newGameBtnClicked() {
        stage = (Stage) mainMenuBtn.getScene().getWindow();
        StartMenu sm = new StartMenu(null);
        sm.setStage(stage);
        sm.newGame();
    }

    @FXML
    private void saveToLBBtnClicked() {

    }
}
