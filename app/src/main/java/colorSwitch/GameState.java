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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class GameState implements Serializable {
    private static final transient int DURATION = 3000;
    private static final transient int ANGLE = 360;

    private Ball ball;
    private Track gameTrack;
    private Integer score;
    private Boolean stateSaved;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Star> stars;
    private ArrayList<ColorChanger> colorChangers;
    private Gameplay gameplay;

    private double lastTapNs;
    private long pausedNow;
    private double translateHalfway;
    private double translateOffset;
    private double maxDisplacement;
    private double shifted;
    private double sceneHalfHeight;
    private Boolean hasEnded;

    // FXML Nodes
    private Text scoreCountNode;
    private Scene scene;

    public GameState(Gameplay gameplay) {
        this.gameplay = gameplay;
        this.score = 0;
        this.stateSaved = false;
        this.hasEnded = false;
        this.obstacles = new ArrayList<Obstacle>();
        this.stars = new ArrayList<Star>();
        this.colorChangers = new ArrayList<ColorChanger>();
    }

    public void setup() {
        // Setup the FXML nodes
        gameplay.getController().setup(gameplay, this);

        // Setup the ball and scene
        scene = gameplay.getScene();

        // Halfway point of frame w.r.t. ball y-tranlate
        sceneHalfHeight = scene.getHeight() / 2;
        translateHalfway = Utils.getAbsoluteY(ball.getNode()) - sceneHalfHeight;
        hasEnded = false;

        // Set spacebar key event handler
        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (codeString.equals("SPACE") && !gameplay.getPaused()) {
                lastTapNs = System.nanoTime();
                translateOffset = ball.getNode().getTranslateY();
            }
        });

        // Set 2 new obstacles on start
        gameTrack.addObstacle();
        gameTrack.addObstacle();
    }

    public void initNodes(
        AnchorPane gameTrackNode,
        Circle ballNode,
        Text scoreCountNode
    ) {
        ball = new Ball(ballNode);
        gameTrack = new Track(this, gameTrackNode);

        this.scoreCountNode = scoreCountNode;
    }

    public void pauseState() {
        pausedNow = System.nanoTime();
    }

    public void playState() {
        lastTapNs += System.nanoTime() - pausedNow;
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
            shifted += delta;

            // Add new obstacle once screen moves half height
            if (shifted > sceneHalfHeight) {
                shifted = 0;
                gameTrack.addObstacle();
            }

            // If ball has reached halfway point, move track down
            if (displacement > translateHalfway) {
                gameTrack.shiftUp(delta);
            }
        }

        // Check for collision with updated states
        checkForCollision();
    }

    private void setScore(Integer delta) {
        this.score += delta;
    }

    public void addObstacle(Obstacle obstacle) {
        if (obstacles.size() > 3) {
            Obstacle oldestObstacle = obstacles.get(0);
            ((AnchorPane) gameTrack.getNode()).getChildren().remove(oldestObstacle.getNode());
            obstacles.remove(0);
        }
        obstacles.add(obstacle);
    }

    public void addStar(Star star) {
        if (stars.size() > 3) {
            Star oldestStar = stars.get(0);
            ((AnchorPane) gameTrack.getNode()).getChildren().remove(oldestStar.getNode());
            stars.remove(0);
        }
        stars.add(star);
    }

    public void addColorChanger(ColorChanger colorChanger) {
        if (colorChangers.size() > 3) {
            ColorChanger oldestColorChanger = colorChangers.get(0);
            ((AnchorPane) gameTrack.getNode()).getChildren().remove(oldestColorChanger.getNode());
            colorChangers.remove(0);
        }
        colorChangers.add(colorChanger);
    }

    private void checkForCollision() {
        // Checking collisions with obstacles
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isColliding(ball)) {
                collisionWithObstacle();
                return;
            }
        }

        // Checking collisions with star
        for (Star star : stars) {
            if (star.isColliding(ball)) {
                collisionWithStar(star);
                return;
            }
        }

        // Checking collisions with color changer
        for (ColorChanger colorChanger : colorChangers) {
            if (colorChanger.isColliding(ball)) {
                collisionWithColorChanger();
                return;
            }
        }
    }

    private void collisionWithObstacle() {
        if (!hasEnded) {
            hasEnded = true;
            gameplay.endGame();
        }
    }

    private void collisionWithStar(Star star) {
        this.score += star.getValue();
        ((Group) star.getNode().getParent()).getChildren().remove(star.getNode());
    }

    private void collisionWithColorChanger() {
        // TODO
        
    }
}
