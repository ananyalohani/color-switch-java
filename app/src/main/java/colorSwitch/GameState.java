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
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import javafx.geometry.Bounds;
import javafx.event.*;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1;

    // Constants
    private static final transient int DURATION = 3000;
    private static final transient int ANGLE = 360;
    private static final transient int MAX_NUMBER_OBJ = 5;
    private static final transient int RESTART_SCORE = 5;

    private Ball ball;
    private Track gameTrack;
    private Integer score;
    private Boolean stateSaved;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Star> stars;
    private ArrayList<ColorChanger> colorChangers;
    private transient Gameplay gameplay;

    private double lastTapNs;
    private long pausedNow;
    private double translateHalfway;
    private double translateOffset;
    private double maxDisplacement;
    private Boolean hasEnded;
    private double trackTranslate;
    private double firstObstacleY;

    // FXML Nodes
    private transient Text scoreCountNode;
    private transient Scene scene;

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
        translateHalfway = Utils.getAbsoluteY(ball.getNode()) - scene.getHeight() / 2;
        hasEnded = false;

        // Set spacebar key event handler
        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();

            if (codeString.equals("SPACE") && !gameplay.getPaused()) {
                lastTapNs = System.nanoTime();
                translateOffset = ball.getNode().getTranslateY();
                Sounds.bounce();
                return;
            }

            if (codeString.equals("P")) {
                gameplay.pauseGame();
            }
        });

        Obstacle.reset();
        Star.reset();

        // Set 2 new obstacles on start
        gameTrack.addObstacle(null);
        gameTrack.addObstacle(null);
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
        for (Obstacle obstacle : obstacles) {
            obstacle.pause();
        }
    }

    public void playState(Boolean initialPlay) {
        double pauseDuration = System.nanoTime() - pausedNow;
        lastTapNs += pauseDuration;
        if (!initialPlay) {
            for (Obstacle obstacle : obstacles) {
                obstacle.play(pausedNow);
            }
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public Integer getScore() {
        return this.score;
    }

    private void setScore(Integer delta) {
        this.score += delta;
        scoreCountNode.setText(score.toString());
    }

    public void setSavedState(Boolean newState) {
        this.stateSaved = newState;
    }

    public Boolean isStateSaved() {
        return stateSaved;
    }

    public void updateState(double now) {
        if (hasEnded) return;

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
                trackTranslate = gameTrack.shiftUp(delta);
            }
        }

        // Check for collision with updated states
        checkForCollision();
    }

    public void addObstacle(Obstacle obstacle) {
        if (obstacles.size() > MAX_NUMBER_OBJ) {
            Obstacle oldestObstacle = obstacles.get(0);
            Group oldestObstacleContainer = (Group) oldestObstacle.getNode().getParent();

            oldestObstacle.stop();
            ((AnchorPane) gameTrack.getNode()).getChildren().remove(oldestObstacleContainer);

            obstacles.remove(0);
        }

        firstObstacleY = obstacles.get(0).getNode().getParent().getTranslateY();
        obstacles.add(obstacle);
    }

    public void addStar(Star star) {
        if (stars.size() > MAX_NUMBER_OBJ) stars.remove(0);
        stars.add(star);
    }

    public void addColorChanger(ColorChanger colorChanger) {
        if (colorChangers.size() > MAX_NUMBER_OBJ) colorChangers.remove(0);
        colorChangers.add(colorChanger);
    }

    private void checkForCollision() {
        // Checking collisions with obstacles
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isColliding(ball)) {
                collisionWithObstacle(obstacle);
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
                collisionWithColorChanger(colorChanger);
                return;
            }
        }
    }

    private void collisionWithObstacle(Obstacle obstacle) {
        if (!hasEnded) {
            hasEnded = true;
            Sounds.collision();

            ((AnchorPane) gameTrack.getNode()).getChildren().remove(ball.getNode());
            ArrayList<Circle> dots = new ArrayList<Circle>();

            ParallelTransition endGameTransitions = getTransitions(dots);
            endGameTransitions.setOnFinished(e -> {
                if (score >= RESTART_SCORE) {
                    AnchorPane _dialogParent = null;
                    AnchorPane gameTrackParent = (AnchorPane) gameTrack.getNode().getParent();

                    try {
                        _dialogParent = FXMLLoader.<AnchorPane>load(
                            getClass().getResource(Constants.Scene.RESTART_DIALOG)
                        );
                    } catch (IOException err) {
                        return;
                    }
                    AnchorPane dialogParent = _dialogParent;

                    Group dialog = (Group) dialogParent.getChildren().get(0);
                    ObservableList<Node> dialogChildren = dialog.getChildren();
                    Button yesBtn = (Button) dialogChildren.get(3);
                    Button noBtn = (Button) dialogChildren.get(4);
                    Text dialogScoreText = (Text) dialogChildren.get(6);

                    dialogScoreText.setText(score.toString());

                    yesBtn.setOnAction(event -> {
                        Bounds obstacleBounds = Utils.getBounds(obstacle.getNode());
                        double obstacleBottomY = obstacleBounds.getMaxY();
                        double ballCurrentAbsoluteY =
                            ball.getNode().getLayoutY() +
                            gameTrack.getNode().getTranslateY() +
                            ball.getNode().getTranslateY();
                        double ballShift = obstacleBottomY - ballCurrentAbsoluteY + 150;
                        double gameTrackShift = ballShift + ballCurrentAbsoluteY > 600
                            ? 600 - (ballShift + ballCurrentAbsoluteY) : 0;

                        maxDisplacement += gameTrackShift;
                        translateOffset = ball.getNode().getTranslateY() + ballShift;
                        ball.reset();

                        App.game.getStats().setStat(Stat.REVIVAL_COUNT, 1, true);
                        this.setScore(-RESTART_SCORE);
                        gameTrackParent.getChildren().remove(dialogParent);
                        for (Circle dot : dots) {
                            ((AnchorPane) gameTrack.getNode()).getChildren().remove(dot);
                        }

                        TranslateTransition gameTrackTranslate = new TranslateTransition(
                            Duration.millis(gameTrackShift == 0 ? 0 : 500), gameTrack.getNode());
                        gameTrackTranslate.setToY(gameTrack.getNode().getTranslateY() + gameTrackShift);
                        gameTrackTranslate.setOnFinished(_event -> {
                            ((AnchorPane) gameTrack.getNode()).getChildren().add(ball.getNode());
                            lastTapNs = System.nanoTime();
                            hasEnded = false;
                        });
                        gameTrackTranslate.play();
                    });

                    noBtn.setOnAction(event -> {
                        gameplay.endGame();
                    });

                    gameTrackParent.getChildren().add(dialogParent);
                    return;
                }

                gameplay.endGame();
            });
            endGameTransitions.play();
        }
    }

    private void collisionWithStar(Star star) {
        stars.remove(0);
        setScore(star.getValue());
        Utils.deleteNode(star.getNode());

        Sounds.score();

        if (score > 3) {
            ball.updateVelocity(score);
        }
    }

    private void collisionWithColorChanger(ColorChanger colorChanger) {
        // Remove color changer from the arraylist
        colorChangers.remove(0);

        // Change the color of the ball
        String color;
        Paint prevColor = ((Circle) ball.getNode()).getFill();
        while (true) {
            int randomIndex = (int) (Math.random() * 4);
            color = Colors.values()[randomIndex].colorCode;
            if (!Paint.valueOf(color).equals(prevColor)) break;
        }
        ((Circle) ball.getNode()).setFill(Paint.valueOf(color));

        // Delete the colorChanger node
        Utils.deleteNode(colorChanger.getNode());

        // Add a new obstacle to the track
        gameTrack.addObstacle(null);
    }

    private ParallelTransition getTransitions(ArrayList<Circle> dotList) {
        AnchorPane gameTrackParent = (AnchorPane) gameTrack.getNode().getParent();
        ParallelTransition transitions = new ParallelTransition();

        // Flash of white
        Rectangle whiteFlash = new Rectangle(0, 0, 500, 800);
        whiteFlash.setFill(Color.WHITE);
        gameTrackParent.getChildren().add(whiteFlash);
        FadeTransition whiteFlashTransition = new FadeTransition(Duration.millis(400), whiteFlash);
        whiteFlashTransition.setToValue(0);
        whiteFlashTransition.setOnFinished(e -> { gameTrackParent.getChildren().remove(whiteFlash); });
        transitions.getChildren().add(whiteFlashTransition);

        // Fade to black
        FadeTransition fadeToBlack = new FadeTransition(Duration.millis(500), gameTrackParent);
        fadeToBlack.setToValue(0);

        // Particle explosion
        Bounds ballBounds = Utils.getBounds(ball.getNode());
        Point origin = new Point(
            ballBounds.getMinX() + ballBounds.getWidth() / 2,
            ballBounds.getMinY() + ballBounds.getHeight() / 2
        );

        for (int i = 0; i < 50; i++) {
            double radius = 2 + Math.random() * 5;
            Circle dot = new Circle(
                250,
                origin.getY(),
                radius,
                Paint.valueOf(
                    Colors.values()[(int) (Math.random() * 4)].colorCode
                )
            );

            dotList.add(dot);
            ((AnchorPane) gameTrack.getNode()).getChildren().add(dot);

            TranslateTransition transition = new TranslateTransition(
                Duration.millis(100 + Math.random() * 600), dot
            );
            transition.setToX(-250 + Math.random() * 500);
            transition.setToY(-400 + Math.random() * 800);
            transition.setInterpolator(Interpolator.EASE_OUT);

            transitions.getChildren().add(
                new SequentialTransition(
                    new PauseTransition(Duration.millis((int) (Math.random() * 400))),
                    transition
                )
            );
        }

        return transitions;
    }
}
