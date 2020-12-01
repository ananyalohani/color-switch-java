import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

public abstract class GameObject implements Serializable {

    protected Point position;
    protected Node node;

    public void setPosition(Point point) {

    }

    public Point getPosition() {
        return this.position;
    }

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}

class Star extends GameObject {
    private Integer value;

    public Integer getValue() {
        return this.value;
    }
}

class ColorChanger extends GameObject {}

class Track extends GameObject {
    private static transient int starCounter = 0;
    private GameState gameState;

    public void shiftUp(double delta) {
        node.setTranslateY(node.getTranslateY() + delta);
    }

    public void addObstacle() {
        int randomIndex = (int) (Math.random() * 4);
        String file = Obstacle.OBSTACLES[randomIndex];

        Obstacle obstacle = null;
        switch (randomIndex) {
            case 0: {
                obstacle = new CircleObstacle(Utils.loadObject(file));
                break;
            }
            case 1: {
                obstacle = new GearsObstacle(Utils.loadObject(file));
                break;
            }
            case 2: {
                obstacle = new SquareObstacle(Utils.loadObject(file));
                break;
            }
            case 3: {
                obstacle = new BarObstacle(Utils.loadObject(file));
                break;
            }
        }

        // Add to game state arraylist
        gameState.addObstacle(obstacle);

        ((AnchorPane) node).getChildren().add(obstacle.getNode());
        obstacle.move();
    }

    public void addStar() {
        Node starNode;
        if(++starCounter % 5 == 0) starNode = Utils.loadObject(FXMLs.GameObject.THREE_STAR);
        else starNode = Utils.loadObject(FXMLs.GameObject.ONE_STAR);
        ((AnchorPane) node).getChildren().add(starNode);
    }

    public void addColorChanger() {
        ((AnchorPane) node).getChildren().add(Utils.loadObject(FXMLs.GameObject.COLOR_CHANGER));
    }

    Track(GameState gameState) {
        this.gameState = gameState;
    }
}
