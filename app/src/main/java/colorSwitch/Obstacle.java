import java.time.OffsetDateTime;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.collections.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.transform.Rotate;
import javafx.geometry.Bounds;

public abstract class Obstacle extends GameObject {
    protected static final transient String OBSTACLES[] = {
        FXMLs.Obstacle.CIRCLE,
        FXMLs.Obstacle.GEARS,
        FXMLs.Obstacle.SQUARE,
        FXMLs.Obstacle.BAR
    };

    protected final double INITIAL_DURATION;
    protected final double WIDTH;
    protected final Point OFFSET;
    protected final double SCENE_WIDTH;

    protected ObstacleShape shape;
    protected Double velocity;
    private ArrayList<ObstacleComponent> components;

    public void calculateVelocity(Integer score) {

    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    protected Double getVelocity() {
        return this.velocity;
    }

    protected void positionSelf() {
        node.setLayoutX((SCENE_WIDTH - WIDTH) / 2 - OFFSET.getX());
        node.setLayoutY(0);
    }

    public abstract void move();

    public abstract void updateSpeed(int score);

    public abstract Boolean isColliding(Ball ball);

    Obstacle(Node node, double duration) {
        Bounds bounds = node.getBoundsInLocal();

        this.node = node;
        this.INITIAL_DURATION = duration;
        this.SCENE_WIDTH = 500;
        this.WIDTH = bounds.getWidth();
        this.OFFSET = new Point(bounds.getMinX(), bounds.getMinY());
    }
}

class CircleObstacle extends Obstacle {
    private final int ANGLE = 360;
    private RotateTransition transition;
    private Rectangle outerBoundingBox;
    private Rectangle innerBoundingBox;
    private Group ring;
    private Timer quarterTimer;
    private int timerCount = 0;
    private ArrayList<Paint> colors;
    private Paint topColor;
    private Paint bottomColor;

    @Override
    public void move() {
        transition = Utils.rotate(ring, (int) INITIAL_DURATION, ANGLE);
        TimerTask changeTopColor = new TimerTask() {
            @Override
            public void run() {
                topColor = colors.get(timerCount % 4);
                bottomColor = colors.get((2 + timerCount++) % 4);
            }
        };
        quarterTimer.scheduleAtFixedRate(changeTopColor, 0, (int) INITIAL_DURATION / 4);
    }

    @Override
    public void updateSpeed(int score) {
        // double newDuration = score; // TODO
        // transition.setDuration(newDuration);
    }

    @Override
    public Boolean isColliding(Ball ball) {
        Paint ballColor = ball.getNode().getFill();
        Bounds ballBounds = Utils.getBounds(ball.getNode());
        Bounds outerBounds = Utils.getBounds(outerBoundingBox);
        Bounds innerBounds = Utils.getBounds(innerBoundingBox);

        if (
            Utils.intersects(ballBounds, outerBounds) &&
            !Utils.isInside(ballBounds, innerBounds)
        ) {
            return ballBounds.getMaxY() > innerBounds.getMaxY()
                ? !ballColor.equals(bottomColor)
                : !ballColor.equals(topColor);
        }

        return false;
    }

    CircleObstacle(Node node) {
        super(node, 3000);

        positionSelf();

        ObservableList<Node> children = ((Group) node).getChildren();
        this.outerBoundingBox = (Rectangle) children.get(0);
        this.innerBoundingBox = (Rectangle) children.get(1);
        this.ring = (Group) children.get(2);

        this.colors = new ArrayList<Paint>();
        for (Node arc : ring.getChildren()) {
            colors.add(((Arc) arc).getStroke());
        }

        this.quarterTimer = new Timer();
    }
}

class BarObstacle extends Obstacle {
    private final double TRANSITION_BY_X = -500;
    TranslateTransition transition;

    @Override
    public void move() {
        transition = new TranslateTransition(Duration.millis(INITIAL_DURATION), node);
        transition.setByX(TRANSITION_BY_X);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    @Override
    public void updateSpeed(int score) {
        double newDuration = score; // TODO
        // transition.setDuration(newDuration);
    }

    @Override
    public Boolean isColliding(Ball ball) {
        ObservableList<Node> components = ((Group) node).getChildren();
        Boolean collision = false;

        for (Node component : components) {
            if (Utils.intersects(ball.getNode(), component)) {
                Paint ballColor = ball.getNode().getFill();
                Paint compColor = ((Rectangle) component).getFill();
                if (!ballColor.equals(compColor)) {
                    collision = true;
                    break;
                }
            }
        }

        return collision;
    }

    BarObstacle(Node node) {
        super(node, 2000);
    }
}

class SquareObstacle extends Obstacle {
    private final int ANGLE = 90;
    private final Point rotationPivot;
    private Timer timer;

    @Override
    public void move() {
        Rotate rotation = new Rotate(ANGLE, rotationPivot.getX(), rotationPivot.getY());

        TimerTask discreteRotation = new TimerTask() {
            @Override
            public void run() {
                node.getTransforms().add(rotation);
            }
        };

        timer.scheduleAtFixedRate(discreteRotation, 0, (long) INITIAL_DURATION);
    }

    @Override
    public void updateSpeed(int Score) {
        // double newDuration = score; // TODO
        // transition.setDuration(newDuration);
    }

    @Override
    public Boolean isColliding(Ball ball) {
        return false;
    }

    SquareObstacle(Node node) {
        super(node, 1000);
        positionSelf();

        this.timer = new Timer();
        this.rotationPivot = new Point(
            WIDTH / 2 + OFFSET.getX(),
            WIDTH / 2 + OFFSET.getY()
        );
    }
}

class GearsObstacle extends Obstacle {
    private final int ANGLE = 360;
    RotateTransition transitionLeft, transitionRight;

    @Override
    public void move() {
        ObservableList<Node> children = ((Group) node).getChildren();
        Node leftGear = children.get(0);
        Node rightGear = children.get(1);
        transitionLeft = Utils.rotate(leftGear, (int) INITIAL_DURATION, -ANGLE);
        transitionRight = Utils.rotate(rightGear, (int) INITIAL_DURATION, ANGLE);
    }

    @Override
    public void updateSpeed(int Score) {

    }

    @Override
    public Boolean isColliding(Ball ball) {
        return false;
    }

    GearsObstacle(Node node) {
        super(node, 6000);
        positionSelf();
    }
}

class ObstacleComponent extends GameObject {
    private Obstacle obstacle;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
