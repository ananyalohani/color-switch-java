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
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import java.util.*;

public abstract class GameObject {
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
    private transient final Paint YELLOW = Color.web("#FFF873"); // ? is this serializable?

    public Integer getValue() {
        return this.value;
    }

    public void position(Point position) {
        node.setLayoutX(position.getX());
        node.setLayoutY(position.getY());
    }

    @Override
    public Boolean isColliding(Ball ball) {
        return Utils.intersects(ball.getNode(), node);
    }

    Star(Node node, GameState gameState) {
        super(node);

        int starCount = gameState.getStarCount();
        int prevStarValue = gameState.getPrevStarValue();

        // 1 pointer star if:
        // * one of the first five
        // * it is the star after a three-pointer
        // * 70% chance
        if (starCount < 5 || prevStarValue == 3 || Math.random() < 0.7) {
            this.value = 1;
        }
        // 3 pointer star // 30% chance
        else {
            this.value = 3;
            ((Text) node).setFill(YELLOW);
            Effect glow = new Glow(1.0);
            ((Text) node).setEffect(glow);
        }

        gameState.setStarCount(starCount + 1);
        gameState.setPrevStarValue(this.value);
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
