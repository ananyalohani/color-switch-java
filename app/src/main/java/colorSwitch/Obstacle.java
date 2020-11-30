import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.transform.Rotate;

public abstract class Obstacle extends GameObject {
    protected static final transient String OBSTACLES[] = {
        FXMLs.Obstacle.CIRCLE,
        FXMLs.Obstacle.TRIANGLE,
        FXMLs.Obstacle.SQUARE,
        FXMLs.Obstacle.BAR
    };

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

    public abstract void move();

    public abstract void updateSpeed(int score);

    public abstract Boolean isColliding(Ball ball);

    Obstacle(Node node) {
        this.node = node;
    }
}

class CircleObstacle extends Obstacle {
    private final int INITIAL_DURATION = 1000;
    private final int ANGLE = 360;
    RotateTransition transition;

    @Override
    public void move() {
        transition = Utils.rotate(node, INITIAL_DURATION, ANGLE);
    }

    @Override
    public void updateSpeed(int score) {
        // double newDuration = score; // TODO
        // transition.setDuration(newDuration);
    }

    @Override
    public Boolean isColliding(Ball ball) {
        return null;
    }

    CircleObstacle(Node node) {
        super(node);
    }
}

class BarObstacle extends Obstacle {
    private final int INITIAL_DURATION = 2000;
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
        return false;
    }

    BarObstacle(Node node) {
        super(node);
    }
}

class SquareObstacle extends Obstacle {
    private final int INITIAL_DURATION = 1000;
    private final int ANGLE = 90;
    private final int OFFSET_X = 12;
    private final int OFFSET_Y = -16;
    private final int WIDTH = 202;
    private final double PIVOT_X = WIDTH / 2 - OFFSET_X;
    private final double PIVOT_Y = WIDTH / 2 - OFFSET_Y;
    RotateTransition transition;

    @Override
    public void move() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                node.getTransforms().add(new Rotate(ANGLE, PIVOT_X, PIVOT_Y));
            }
        }, 0, INITIAL_DURATION);
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
        super(node);
        node.setLayoutX(250 - WIDTH / 2 + OFFSET_X);
    }
}

class GearObstacle extends Obstacle {
    @Override
    public void move() {

    }

    @Override
    public void updateSpeed(int Score) {

    }

    @Override
    public Boolean isColliding(Ball ball) {
        return false;
    }

    GearObstacle(Node node) {
        super(node);
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
