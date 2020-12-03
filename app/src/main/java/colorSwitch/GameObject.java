import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import java.util.*;

public abstract class GameObject implements Serializable {
    private static final long serialVersionUID = 1;

    protected Point position;
    protected transient Node node;
    protected final double WIDTH;
    protected final double HEIGHT;
    protected final Point OFFSET;
    protected final double SCENE_WIDTH = 500;
    protected final double SCENE_HEIGHT = 800;
    protected final double INITIAL_OFFSET_Y;

    public void setPosition(Point point) {}

    public Point getPosition() {
        return this.position;
    }

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public abstract Boolean isColliding(Ball ball);

    GameObject(Node node) {
        Bounds bounds = node.getBoundsInLocal();
        Bounds absoluteBounds = Utils.getBounds(node);

        this.node = node;
        this.WIDTH = bounds.getWidth();
        this.HEIGHT = bounds.getHeight();
        this.OFFSET = new Point(bounds.getMinX(), bounds.getMinY());
        this.INITIAL_OFFSET_Y =
            SCENE_HEIGHT / 2 - absoluteBounds.getMinY() - (absoluteBounds.getHeight() / 2) - 100;
    }
}

class Star extends GameObject {
    private Integer value;
    private Obstacle obstacle;
    private final Paint YELLOW = Color.web("#FFF873"); // ? is this serializable?
    private static int starCount = 0;
    private static int prevStarValue = 1;

    public Integer getValue() {
        return this.value;
    }

    public void position(Point position) {
        node.setLayoutX(position.getX());
        node.setLayoutY(position.getY());
    }

    public static void reset() {
        starCount = 0;
        prevStarValue = 1;
    }

    @Override
    public Boolean isColliding(Ball ball) {
        return Utils.intersects(ball.getNode(), node);
    }

    Star(Node node) {
        super(node);

        starCount++;

        // 1 pointer star if:
        // * one of the first five
        // * it is the star after a three-pointer
        // * 70% chance
        if (starCount < 5 || prevStarValue == 3 || Math.random() < 0.7) {
            this.value = prevStarValue = 1;
        }
        // 3 pointer star // 30% chance
        else {
            this.value = prevStarValue = 3;
            ((Text) node).setFill(YELLOW);
        }
    }
}

class ColorChanger extends GameObject {
    @Override
    public Boolean isColliding(Ball ball) {
        return Utils.intersects(ball.getNode(), node);
    }

    ColorChanger(Node node) {
        super(node);
        Utils.rotate(node, 3000, 360);
    }
}
