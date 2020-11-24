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
import java.util.*;

public class GameplayController {
    private static final int FPS = 60;
    private static final long FPS_NS_PERIOD = 1_000_000_000 / FPS;
    private static final int DURATION = 3000;
    private static final int ANGLE = 360;
    private static final int BALL_JUMP_SIZE = 100; // pixels
    private static final int BALL_JUMP_DURATION = 200; // milliseconds
    private static final double BALL_INITIAL_VELOCITY = (double) BALL_JUMP_SIZE / BALL_JUMP_DURATION * 1000;
    private static final double GRAVITY_ACCN = -Math.pow(BALL_INITIAL_VELOCITY, 2)  / (2 * BALL_JUMP_SIZE);

    private long lastTapNs;
    private Gameplay gameplay;
    private Stage stage;
    private Scene scene;
    private Timeline ballTimeline;
    private RotateTransition obsRT1, obsRT2;
    int i = 0;

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

    public void animPlay() {
        // ballTimeline.play();
        obsRT1.play();
        obsRT2.play();
    }

    private void animPause() {
        // ballTimeline.pause();
        obsRT1.pause();
        obsRT2.pause();
    }

    public void moveBall() {
    }

    public void initData(Gameplay gameplay, Stage stage, Scene scene) {
        this.gameplay = gameplay;
        this.stage = stage;
        this.scene = scene;
        this.lastTapNs = System.nanoTime();

        // Space bar key event handlers
        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (codeString.equals("SPACE")) {
                lastTapNs = System.nanoTime();
                double newY = ball.getLayoutY() + ball.getTranslateY();
                ball.setLayoutY(newY);
            }
        });

        scene.setOnKeyReleased(event -> {});

        new AnimationTimer() {
            private long lastUpdated = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdated >= FPS_NS_PERIOD) {
                    double duration = (double) (now - lastTapNs) / 1_000_000_000;

                    double displacement =
                        BALL_INITIAL_VELOCITY * duration + 0.5 * GRAVITY_ACCN * Math.pow(duration, 2);
                    ball.setTranslateY(-displacement);
                }
            }
        }.start();
    }

    @FXML
    private void pauseBtnClicked() {
        animPause();
        PauseMenu pm = new PauseMenu(gameplay, pauseBtn.getScene(), this);
        pm.setStage(stage);
        pm.displayMenu();
    }
}
