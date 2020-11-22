import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;
import javafx.animation.*;

public class GameplayController {
    private Gameplay gameplay;
    private Stage stage;
    private static final int DURATION = 3000;
    private static final int ANGLE = 360;
    private static final int BALL_JUMP_SIZE = 100;
    private static final int BALL_FLIGHT_DURATION = 500;

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

        moveBall();
    }

    Interpolator gravityInterpolator = new Interpolator() {
        @Override
        protected double curve​(double t) {
            return t * (2 - t);
        }
    };

    public void moveBall() {
        double ballY = ball.getTranslateY();

        KeyValue kv1 = new KeyValue(ball.translateYProperty(), ballY, gravityInterpolator);
        KeyValue kv2 = new KeyValue(ball.translateYProperty(), ballY - BALL_JUMP_SIZE, gravityInterpolator);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, kv1),
            new KeyFrame(Duration.millis(BALL_FLIGHT_DURATION), kv2)
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
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
