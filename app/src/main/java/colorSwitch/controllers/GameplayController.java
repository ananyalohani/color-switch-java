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

public class GameplayController {
    private Gameplay gameplay;
    private Stage stage;
    private static final int DURATION = 3000;
    private static final int ANGLE = 360;

    @FXML
    private Circle ball, colorChanger;

    @FXML
    private Text scoreCount;

    @FXML
    private Group pauseBtn, obs1, obs2;

    public GameplayController() {}

    @FXML
    public void initialize() {
        Utils.rotate(obs1, DURATION, ANGLE);
        Utils.rotate(obs2, DURATION, -ANGLE);
    }

    public void initData(Gameplay gameplay, Stage stage) {
        this.gameplay = gameplay;
        this.stage = stage;
    }

    @FXML
    private void pauseBtnClicked() {
        PauseMenu pm = new PauseMenu(gameplay);
        pm.setStage(stage);
        pm.displayMenu();
    }
}
