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
import javafx.util.Duration;

public class GameState implements Serializable {
    private static final transient int DURATION = 3000;
    private static final transient int ANGLE = 360;

    private Ball ball;
    private Track gameTrack;
    private ColorChanger colorChanger;
    private Integer score;
    private Boolean stateSaved;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Star> stars;
    private RotateTransition obsRT1, obsRT2;
    private Gameplay gameplay;

    private double lastTapNs;
    private long pausedNow;
    private double translateHalfway;
    private double translateOffset;
    private double maxDisplacement;

    // FXML Nodes
    private Text scoreCountNode;
    private Group obs1Node, obs2Node;
    private Scene scene;

    public GameState(Gameplay gameplay) {
        this.gameplay = gameplay;
        this.score = 0;
        this.stateSaved = false;
        this.ball = new Ball();
        this.gameTrack = new Track();
        this.colorChanger = new ColorChanger();
        this.obstacles = new ArrayList<Obstacle>();
        this.stars = new ArrayList<Star>();
    }

    public void setup() {
        // Setup the FXML nodes
        gameplay.getController().setup(gameplay, this);

        // Setup the ball and scene
        scene = gameplay.getScene();

        // Halfway point of frame w.r.t. ball y-tranlate
        translateHalfway = Utils.getAbsoluteY(ball.getNode()) - scene.getHeight() / 2;

        // Set spacebar key event handler
        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (codeString.equals("SPACE") && !gameplay.getPaused()) {
                lastTapNs = System.nanoTime();
                translateOffset = ball.getNode().getTranslateY();
            }
        });

        // Start animations
        obsRT1 = Utils.rotate(obs1Node, DURATION, ANGLE);
        obsRT2 = Utils.rotate(obs2Node, DURATION, -ANGLE);
    }

    public void initNodes(
        AnchorPane gameTrackNode,
        Circle ballNode,
        Circle colorChangerNode,
        Text scoreCountNode,
        Group obs1Node,
        Group obs2Node
    ) {
        ball.setNode(ballNode);
        gameTrack.setNode(gameTrackNode);
        colorChanger.setNode(colorChangerNode);
        this.scoreCountNode = scoreCountNode;
        this.obs1Node = obs1Node;
        this.obs2Node = obs2Node;
    }

    public void pauseState() {
        pausedNow = System.nanoTime();
        obsRT1.pause();
        obsRT2.pause();
    }

    public void playState() {
        lastTapNs += System.nanoTime() - pausedNow;
        obsRT1.play();
        obsRT2.play();
    }

    public Integer getScore() {
        return this.score;
    }

    public void setSavedState(Boolean newState) {
        this.stateSaved = newState;
    }

    public Boolean isStateSaved() {
        return stateSaved;
    }

    public void updateState(double now) {
        // Calculate state vars
        double duration = (double) (now - lastTapNs) / 1_000_000_000;

        // Move ball to new position and get position
        double displacement = ball.move(duration, translateOffset);

        // Get and set highest point reached by ball
        if (displacement > maxDisplacement) {
            double delta = displacement - maxDisplacement;
            maxDisplacement = displacement;

            // If ball has reached halfway point, move track down
            if (displacement > translateHalfway) {
                gameTrack.shiftUp(delta);
            }
        }
    }

    private void setScore(Integer delta) {
        this.score += delta;
    }

    private Boolean checkForCollision() {
        return null;
    }

    private void collisionWithStar() {

    }

    private void collisionWithObstacle() {

    }
}
