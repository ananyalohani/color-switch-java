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
    private Timeline ballTimeline;
    private RotateTransition obsRT1, obsRT2;

    @FXML
    private Circle ball, colorChanger;

    @FXML
    private Text scoreCount;

    @FXML
    private Group pauseBtn, obs1, obs2;

    public GameplayController() {}

    @FXML
    public void initialize() {
        obsRT1 = Utils.rotate(obs1, DURATION, ANGLE);
        obsRT2 = Utils.rotate(obs2, DURATION, -ANGLE);

        moveBall();
    }

    Interpolator gravityInterpolator = new Interpolator() {
        @Override
        protected double curve​(double t) {
            return t * (2 - t);
        }
    };

    public void animPlay() {
        ballTimeline.play();
        obsRT1.play();
        obsRT2.play();
    }

    private void animPause() {
        ballTimeline.pause();
        obsRT1.pause();
        obsRT2.pause();
    }

    public void moveBall() {
        double ballY = ball.getTranslateY();

        KeyValue kv1 = new KeyValue(ball.translateYProperty(), ballY, gravityInterpolator);
        KeyValue kv2 = new KeyValue(ball.translateYProperty(), ballY - BALL_JUMP_SIZE, gravityInterpolator);

        ballTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, kv1),
            new KeyFrame(Duration.millis(BALL_FLIGHT_DURATION), kv2)
        );

        ballTimeline.setCycleCount(Animation.INDEFINITE);
        ballTimeline.setAutoReverse(true);
        ballTimeline.play();
    }

    public void initData(Gameplay gameplay, Stage stage) {
        this.gameplay = gameplay;
        this.stage = stage;
    }

    @FXML
    private void pauseBtnClicked() {
        animPause();
        PauseMenu pm = new PauseMenu(gameplay, pauseBtn.getScene(), this);
        pm.setStage(stage);
        pm.displayMenu();
    }
}
