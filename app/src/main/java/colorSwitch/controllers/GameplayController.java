import java.io.*;
import java.util.*;
import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameplayController {
    private Gameplay gameplay;

    @FXML
    private AnchorPane gameTrack;

    @FXML
    private Circle ball, colorChanger;

    @FXML
    private Text scoreCount;

    @FXML
    private Group pauseBtn;

    @FXML
    private void pauseBtnClicked() {
        gameplay.pauseGame();
    }

    public void setup(Gameplay gameplay, GameState gameState) {
        this.gameplay = gameplay;
        gameState.initNodes(gameTrack, ball, colorChanger, scoreCount);
    }

    public GameplayController() {}
}
