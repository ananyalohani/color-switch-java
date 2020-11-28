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
    private Integer value;

    public Integer getValue() {
        return this.value;
    }
}

class ColorChanger extends GameObject {}

class Track extends GameObject {
    public void shiftUp(double delta) {
        node.setTranslateY(node.getTranslateY() + delta);
    }
}
