import java.io.Serializable;
import javafx.scene.Node;

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
    public static final transient String ONE_STAR = "game-objects/oneStar.fxml";
    public static final transient String THREE_STAR = "game-objects/threeStar.fxml";

    private Integer value;

    public Integer getValue() {
        return this.value;
    }
}

class ColorChanger extends GameObject {
    public static final transient String COLOR_CHANGER = "game-objects/colorChanger.fxml";
}

class Track extends GameObject {
    private static transient int starCounter = 0;

    public void shiftUp(double delta) {
        node.setTranslateY(node.getTranslateY() + delta);
    }

    public void addObstacle() {
        int ind = (int) (Math.random() * 4);
        String file = Obstacle.OBSTACLES[ind];
        Node obsNode = Utils.loadObject(file);
        ((AnchorPane)node).getChildren().add(obsNode);
    }

    public void addStar() {
        Node starNode;
        if(++starCounter % 5 == 0) starNode = Utils.loadObject(Star.THREE_STAR);
        else starNode = Utils.loadObject(Star.THREE_STAR);
        ((AnchorPane)node).getChildren().add(starNode);
    }

    public void addColorChanger() {
        ((AnchorPane)node).getChildren().add(Utils.loadObject(ColorChanger.COLOR_CHANGER));
    }
}
