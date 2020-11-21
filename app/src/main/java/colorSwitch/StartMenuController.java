import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class StartMenuController {
    private StartMenu startMenu;

    @FXML
    private Group exitBtn, playBtn, helpBtn, settingsBtn;

    @FXML
    private Button savedGamesBtn, leaderboardBtn, statsBtn;

    @FXML
    private Text score;

    public StartMenuController() {}

    @FXML
    private void initialize() {}

    public void initData(StartMenu startMenu) {
        this.startMenu = startMenu;
    }

    @FXML
    private void exitBtnClicked() {
        startMenu.exit();
    }

    @FXML
    private void playBtnClicked() {

    }

    @FXML
    private void helpBtnClicked() {

    }

    @FXML
    private void settingsBtnClicked() {

    }

    @FXML
    private void savedGamesBtnClicked() {

    }

    @FXML
    private void leaderboardBtnClicked() {

    }

    @FXML
    private void statsBtnClicked() {

    }
}
