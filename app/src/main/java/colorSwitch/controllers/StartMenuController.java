import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


public class StartMenuController {
    private StartMenu startMenu;
    private Scene settingsScene;
    private static final int DURATION = 3000;
    private static final int ANGLE = 360;

    @FXML
    private Group exitBtn, playBtn, helpBtn, settingsBtn;

    @FXML
    private Button savedGamesBtn, statsBtn;

    @FXML
    private Text score;

    @FXML
    private Group c1, c2, c3, o1, o2;

    public StartMenuController() {}

    @FXML
    private void initialize() {
        Utils.rotate(c1, DURATION, ANGLE);
        Utils.rotate(c2, DURATION, -ANGLE);
        Utils.rotate(c3, DURATION, ANGLE);
        Utils.rotate(o1, DURATION, ANGLE);
        Utils.rotate(o2, DURATION, -ANGLE);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLs.Scene.SETTINGS));

            settingsScene = new Scene(loader.load());
            SettingsController sController = loader.getController();
            sController.setup(startMenu);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setup(StartMenu startMenu) {
        this.startMenu = startMenu;
    }

    @FXML
    private void exitBtnClicked() {
        startMenu.exit();
    }

    @FXML
    private void playBtnClicked() {
        startMenu.newGame();
    }

    @FXML
    private void helpBtnClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLs.Scene.HELP));

            Scene scene = new Scene(loader.load());
            startMenu.getStage().setScene(scene);

            HelpController helpController = loader.getController();
            helpController.setup(startMenu);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void settingsBtnClicked() {
        startMenu.getStage().setScene(settingsScene);
    }

    @FXML
    private void savedGamesBtnClicked() {
        startMenu.displaySavedGames();
    }

    @FXML
    private void leaderboardBtnClicked() {

    }

    @FXML
    private void statsBtnClicked() {

    }
}
