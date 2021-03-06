import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.collections.*;
import java.util.*;

public class Track extends GameObject {
    private GameState gameState;
    private static final int SCORE_THRESHOLD = 3;
    private int starCount = 0;
    private int prevStarValue = 1;

    @Override
    public Boolean isColliding(Ball ball) { return null; }

    public double shiftUp(double delta) {
        double newTranslate = node.getTranslateY() + delta;
        node.setTranslateY(newTranslate);
        return newTranslate;
    }

    public void addObstacle(ObstacleShape shape) {
        int shapeIndex;
        if (shape == null) {
            // Get a random index and associated obstacle file
            shapeIndex = (int) (Math.random() * 3) +
                (gameState.getScore() >= SCORE_THRESHOLD ? 1 : 0);
        } else {
            shapeIndex = Arrays.asList(ObstacleShape.values()).indexOf(shape);
        }

        String file = Obstacle.OBSTACLES[shapeIndex];

        // Load that obstacle container containing the
        // obstacle, color changer and the star
        Group obstacleContainer = (Group) Utils.loadObject(file);
        ObservableList<Node> children = obstacleContainer.getChildren();

        Group obstacleNode = (Group) children.get(0);
        Text starNode = (Text) children.get(1);
        Group colorChangerNode = (Group) children.get(2);

        // Create respective new objects for each,
        // using switch-case for object
        Obstacle obstacle = null;
        ColorChanger colorChanger = new ColorChanger(colorChangerNode);
        Star star = new Star(starNode, gameState);

        switch (shapeIndex) {
            case 0: {
                obstacle = new CircleObstacle(obstacleNode);
                break;
            }
            case 1: {
                obstacle = new BarObstacle(obstacleNode);
                break;
            }
            case 2: {
                obstacle = new SquareObstacle(obstacleNode);
                break;
            }
            case 3: {
                obstacle = new GearsObstacle(obstacleNode);
                break;
            }
        }

        // Add to game state arraylist
        gameState.addObstacle(obstacle);
        gameState.addStar(star);
        gameState.addColorChanger(colorChanger);

        // Add the entire container (obstacle+star+color changer) to the track
        ((AnchorPane) node).getChildren().add(obstacleContainer);

        // Position the obstacle and stars and color changer
        // Don't center align barObstacle
        obstacle.positionSelf(shapeIndex != 1, obstacleContainer);

        // Start transitions on the object
        obstacle.move();
    }

    Track(GameState gameState, Node node) {
        super(node);
        this.gameState = gameState;
    }
}
