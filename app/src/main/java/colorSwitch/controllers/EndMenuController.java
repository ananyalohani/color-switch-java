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

    public void initData(EndMenu endMenu, Stage stage) {
        this.endMenu = endMenu;
        this.stage = stage;
    }

    @FXML
    private void mainMenuBtnClicked() {
        endMenu.exit();
    }

    @FXML
    private void newGameBtnClicked() {
        endMenu.newGame();
    }

    @FXML
    private void saveToLBBtnClicked() {

    }
}
