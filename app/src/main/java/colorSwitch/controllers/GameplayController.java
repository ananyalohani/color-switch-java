import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.*;
import javafx.scene.layout.*;
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
    private static final double BALL_INITIAL_VELOCITY
        = (double) BALL_JUMP_SIZE / BALL_JUMP_DURATION * 1000;
    private static final double GRAVITY_ACCN
        = -Math.pow(BALL_INITIAL_VELOCITY, 2)  / (2 * BALL_JUMP_SIZE);

    private boolean paused = false;
    private long pausedNow;
    private long lastTapNs;
    private double translateHalfway;
    private double translateOffset;
    private double farthestDistance;
    private Gameplay gameplay;
    private Stage stage;
    private Scene scene;
    private Timeline ballTimeline;
    private RotateTransition obsRT1, obsRT2;

    @FXML
    private AnchorPane gameTrack;

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
        lastTapNs = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (paused) return;
                double duration = (double) (now - lastTapNs) / 1_000_000_000; 
                double displacement =
                    BALL_INITIAL_VELOCITY * duration + 0.5 * GRAVITY_ACCN * Math.pow(duration, 2);
                double resolvedDisplacement = displacement - translateOffset;

                ball.setTranslateY(-resolvedDisplacement);

                if (resolvedDisplacement > farthestDistance) {
                    double delta = resolvedDisplacement - farthestDistance;
                    farthestDistance = resolvedDisplacement;
                    if (resolvedDisplacement > translateHalfway) {
                        gameTrack.setTranslateY(gameTrack.getTranslateY() + delta);
                    }
                }
            }
        }.start();
    }

    public void gameplayPlay() {
        obsRT1.play();
        obsRT2.play();
        paused = false;
        lastTapNs += System.nanoTime() - pausedNow;
    }

    private void gameplayPause() {
        obsRT1.pause();
        obsRT2.pause();
        paused = true;
        pausedNow = System.nanoTime();
    }

    public void initData(Gameplay gameplay, Stage stage, Scene scene) {
        this.gameplay = gameplay;
        this.stage = stage;
        this.scene = scene;
        translateHalfway = Utils.getAbsoluteY(ball) - scene.getHeight() / 2;

        // Space bar key event handlers
        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (codeString.equals("SPACE")) {
                lastTapNs = System.nanoTime();
                translateOffset = ball.getTranslateY();
            }
        });

        scene.setOnKeyReleased(event -> {});
    }

    @FXML
    private void pauseBtnClicked() {
        gameplayPause();
        PauseMenu pm = new PauseMenu(gameplay, pauseBtn.getScene(), this);
        pm.setStage(stage);
        pm.displayMenu();
    }
}
