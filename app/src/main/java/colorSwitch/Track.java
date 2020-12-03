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

    @Override
    public Boolean isColliding(Ball ball) { return null; }

    public void shiftUp(double delta) {
        node.setTranslateY(node.getTranslateY() + delta);
    }

    public void addObstacle() {
        // Get a random index and associated obstacle file
        int randomIndex = (int) (Math.random() * 4);
        String file = Obstacle.OBSTACLES[randomIndex];

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
        Star star = new Star(starNode);
        ColorChanger colorChanger = new ColorChanger(colorChangerNode);

        switch (randomIndex) {
            case 0: {
                obstacle = new CircleObstacle(obstacleNode);
                break;
            }
            case 1: {
                obstacle = new GearsObstacle(obstacleNode);
                break;
            }
            case 2: {
                obstacle = new SquareObstacle(obstacleNode);
                break;
            }
            case 3: {
                obstacle = new BarObstacle(obstacleNode);
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
        obstacle.positionSelf(randomIndex != 3, obstacleContainer);

        // Start transitions on the object
        obstacle.move();
    }

    Track(GameState gameState, Node node) {
        super(node);
        this.gameState = gameState;
    }
}
