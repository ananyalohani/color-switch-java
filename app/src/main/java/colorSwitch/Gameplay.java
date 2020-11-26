import java.io.*;
import java.util.*;
import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gameplay {
    // Constants
    private static final int FPS = 60;
    private static final long FPS_NS_PERIOD = 1_000_000_000 / FPS;
    private static final int DURATION = 3000;
    private static final int ANGLE = 360;
    private static final int BALL_JUMP_SIZE = 100; // pixels
    private static final int BALL_JUMP_DURATION = 200; // milliseconds
    private static final double BALL_VELOCITY = Physics.velocity(BALL_JUMP_SIZE, BALL_JUMP_DURATION);
    private static final double BALL_DOWNWARD_ACCN = Physics.acceleration(BALL_VELOCITY, BALL_JUMP_SIZE);

    // State variables
    private boolean paused = false;
    private long pausedNow;
    private long lastTapNs;
    private double translateHalfway;
    private double translateOffset;
    private double farthestDistance;
    private Timeline ballTimeline;
    private RotateTransition obsRT1, obsRT2;
    private AnimationTimer renderLoop;

    // FXML Nodes
    private AnchorPane gameTrack;
    private Circle ball, colorChanger;
    private Text scoreCount;
    private Group pauseBtn, obs1, obs2;
    private Stage stage;
    private Scene scene;

    // Menus and states
    private GameState currentState;
    private PauseMenu pauseMenu;
    private EndMenu endMenu;

    public Gameplay(SavedGame savedGame, Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }

    public Gameplay(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }

    public void initNodes(
        AnchorPane gameTrack,
        Circle ball,
        Circle colorChanger,
        Text scoreCount,
        Group pauseBtn,
        Group obs1,
        Group obs2
    ) {
        this.gameTrack = gameTrack;
        this.ball = ball;
        this.colorChanger = colorChanger;
        this.scoreCount = scoreCount;
        this.pauseBtn = pauseBtn;
        this.obs1 = obs1;
        this.obs2 = obs2;
    }

    public void start() {
        // Halfway point of frame w.r.t. ball y-tranlate
        translateHalfway = Utils.getAbsoluteY(ball) - scene.getHeight() / 2;

        // Render loop
        renderLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Don't calc/move if game paused
                if (paused) return;

                // Calculate state vars
                double duration = (double) (now - lastTapNs) / 1_000_000_000; 
                double displacement = Physics.displacement(BALL_VELOCITY, duration, BALL_DOWNWARD_ACCN);
                double resolvedDisplacement = displacement - translateOffset;

                // Move ball to new position
                ball.setTranslateY(-resolvedDisplacement);

                // Get and set highest point reached by ball
                if (resolvedDisplacement > farthestDistance) {
                    double delta = resolvedDisplacement - farthestDistance;
                    farthestDistance = resolvedDisplacement;

                    // If ball has reached halfway point, move track down
                    if (resolvedDisplacement > translateHalfway) {
                        gameTrack.setTranslateY(gameTrack.getTranslateY() + delta);
                    }
                }
            }
        };

        // Set spacebar key event handler
        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (codeString.equals("SPACE")) {
                lastTapNs = System.nanoTime();
                translateOffset = ball.getTranslateY();
            }
        });

        // Start animations, set initial var values
        obsRT1 = Utils.rotate(obs1, DURATION, ANGLE);
        obsRT2 = Utils.rotate(obs2, DURATION, -ANGLE);
        playGame();

        // Start render loop
        renderLoop.start();
    }

    public void saveGame() {

    }

    public void pauseGame() {
        obsRT1.pause();
        obsRT2.pause();
        paused = true;
        pausedNow = System.nanoTime();

        // Pause menu setup
        PauseMenu pm = new PauseMenu(this, pauseBtn.getScene());
        pm.setStage(stage);
        pm.displayMenu();
    }

    public void playGame() {
        obsRT1.play();
        obsRT2.play();
        paused = false;
        lastTapNs += System.nanoTime() - pausedNow;
    }

    public void endGame() {

    }
}
