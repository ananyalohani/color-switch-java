import java.time.OffsetDateTime;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;
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

    private static transient double lastObstacleY;

    private static final transient double FIXED_GAP = 400;
    protected final double INITIAL_PERIOD;
    protected double lastTaskNs;
    protected double expectedNextTaskNs;

    protected ObstacleShape shape;
    protected ArrayList<ObstacleComponent> components;
    protected int count = 0;

    protected long getDelay(double period, double pausedNs) {
        double delay = period * 1_000_000 - (pausedNs - lastTaskNs);
        while (delay < 0) delay += period * 1_000_000;
        return (long) delay / 1_000_000;
    }

    public static void reset() {
        lastObstacleY = 0;
    }

    public void positionSelf(Boolean positionCenter, Node obstacleContainer) {
        if (positionCenter) {
            obstacleContainer.setLayoutX(
                (SCENE_WIDTH - WIDTH) / 2 - obstacleContainer.getBoundsInLocal().getMinX()
            );
        }

        obstacleContainer.setTranslateY(INITIAL_OFFSET_Y + lastObstacleY);
        this.lastObstacleY -= FIXED_GAP + HEIGHT / 2;
    }

    public abstract void move();

    public abstract void updateSpeed(int score);

    public abstract void stop();

    public abstract void pause();

    public abstract void play(double pausedNs);

    Obstacle(Node node, double duration) {
        super(node);
        this.INITIAL_PERIOD = duration;
        components = Utils.getComponents(this, ((Group) node).getChildren());
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
    private int id;
    private static int count = 0;

    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                topColor = colors.get(timerCount % 4);
                bottomColor = colors.get((2 + timerCount++) % 4);
                lastTaskNs = System.nanoTime();
            }
        };
    }

    @Override
    public void move() {
        transition = Utils.rotate(ring, (int) INITIAL_PERIOD, ANGLE);
        expectedNextTaskNs = System.nanoTime() + (INITIAL_PERIOD / 4) * 1_000_000;
        quarterTimer.scheduleAtFixedRate(getTimerTask(), 0, (int) INITIAL_PERIOD / 4);
    }

    @Override
    public void pause() {
        transition.pause();
        quarterTimer.cancel();
    }

    @Override
    public void play(double pausedNs) {
        // After a timer is paused, we must initiate new timer and timertask
        quarterTimer = new Timer();
        transition.play(); // Play the transition

        // Calculate the delay
        long delay = getDelay(INITIAL_PERIOD / 4, pausedNs);
        // Scehdule that timer again with that delay
        quarterTimer.scheduleAtFixedRate(getTimerTask(), delay, (int) INITIAL_PERIOD / 4);
    }

    @Override
    public void updateSpeed(int score) {
        // double newDuration = score; // TODO
        // transition.setDuration(newDuration);
    }

    @Override
    public Boolean isColliding(Ball ball) {
        Paint ballColor = ((Circle) ball.getNode()).getFill();
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

    @Override
    public void stop() {
        quarterTimer.cancel();
        transition.stop();
    }

    CircleObstacle(Node node) {
        super(node, 3000);

        id = count++;
        this.outerBoundingBox = (Rectangle) components.get(0).getNode();
        this.innerBoundingBox = (Rectangle) components.get(1).getNode();
        this.ring = (Group) components.get(2).getNode();

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
        transition = new TranslateTransition(Duration.millis(INITIAL_PERIOD), node);
        transition.setByX(TRANSITION_BY_X);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    @Override
    public void pause() {
        transition.pause();
    }

    @Override
    public void play(double pausedNs) {
        transition.play();
    }

    @Override
    public void updateSpeed(int score) {
        double newDuration = score; // TODO
        // transition.setDuration(newDuration);
    }

    @Override
    public Boolean isColliding(Ball ball) {
        Boolean collision = false;

        for (ObstacleComponent component : components) {
            if (Utils.intersects(ball.getNode(), component.getNode())) {
                Paint ballColor = ((Circle) ball.getNode()).getFill();
                Paint compColor = ((Rectangle) component.getNode()).getFill();
                if (!ballColor.equals(compColor)) {
                    collision = true;
                    break;
                }
            }
        }

        return collision;
    }

    @Override
    public void stop() {
        transition.stop();
    }

    BarObstacle(Node node) {
        super(node, 2000);
    }
}

class SquareObstacle extends Obstacle {
    private final int ANGLE = 90;
    private final Point rotationPivot;
    private Timer quarterTimer;

    private TimerTask getTimerTask() {
        return new TimerTask() {
            Rotate rotation = new Rotate(ANGLE, rotationPivot.getX(), rotationPivot.getY());

            @Override
            public void run() {
                node.getTransforms().add(rotation);
                lastTaskNs = System.nanoTime();
            }
        };
    }

    @Override
    public void move() {
        quarterTimer.scheduleAtFixedRate(getTimerTask(), 0, (long) INITIAL_PERIOD);
    }

    @Override
    public void pause() {
        quarterTimer.cancel();
    }

    @Override
    public void play(double pausedNs) {
        long delay = getDelay(INITIAL_PERIOD, pausedNs);
        quarterTimer = new Timer();
        quarterTimer.scheduleAtFixedRate(getTimerTask(), delay, (int) INITIAL_PERIOD);
    }

    @Override
    public void updateSpeed(int Score) {
        // double newDuration = score; // TODO
        // transition.setDuration(newDuration);
    }

    @Override
    public Boolean isColliding(Ball ball) {
        Boolean collision = false;

        for (ObstacleComponent component : components) {
            if (Utils.intersects(ball.getNode(), component.getNode())) {
                Paint ballColor = ((Circle) ball.getNode()).getFill();
                Paint compColor = ((Rectangle) component.getNode()).getFill();
                if (!ballColor.equals(compColor)) {
                    collision = true;
                    break;
                }
            }
        }

        return collision;
    }

    @Override
    public void stop() {
        quarterTimer.cancel();
    }

    SquareObstacle(Node node) {
        super(node, 1000);

        this.rotationPivot = new Point(
            WIDTH / 2 + OFFSET.getX(),
            WIDTH / 2 + OFFSET.getY()
        );

        this.quarterTimer = new Timer();
    }
}

class GearsObstacle extends Obstacle {
    private final int ANGLE = 360;
    RotateTransition transitionLeft, transitionRight;
    private Timer quarterTimer;
    private int timerCount = 0;
    private ArrayList<Paint> colors;
    private Paint middleColor;
    private Rectangle criticalRegion;
    private Group leftGear, rightGear;

    private TimerTask getMiddleColorTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                lastTaskNs = System.nanoTime();
                middleColor = colors.get(timerCount++ % 4);
            }
        };
    }

    private TimerTask getNullerTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                middleColor = null;
            }
        };
    }

    @Override
    public void move() {
        transitionLeft = Utils.rotate(leftGear, (int) INITIAL_PERIOD, ANGLE);
        transitionRight = Utils.rotate(rightGear, (int) INITIAL_PERIOD, -ANGLE);

        quarterTimer.scheduleAtFixedRate(
            getMiddleColorTimerTask(), 0, (long) INITIAL_PERIOD / 4);
        quarterTimer.scheduleAtFixedRate(
            getNullerTimerTask(), (long) INITIAL_PERIOD / 18, (long) INITIAL_PERIOD / 4);
    }

    @Override
    public void pause() {
        transitionLeft.pause();
        transitionRight.pause();
        quarterTimer.cancel();
    }

    @Override
    public void play(double pausedNs) {
        transitionLeft.play();
        transitionRight.play();

        long delay = getDelay(INITIAL_PERIOD / 4, pausedNs);
        quarterTimer = new Timer();
        quarterTimer.scheduleAtFixedRate(
            getMiddleColorTimerTask(), delay, (long) INITIAL_PERIOD / 4);
        quarterTimer.scheduleAtFixedRate(
            getNullerTimerTask(), delay + (long) INITIAL_PERIOD / 18, (long) INITIAL_PERIOD / 4);
    }

    @Override
    public void updateSpeed(int Score) {

    }

    @Override
    public Boolean isColliding(Ball ball) {
        if (Utils.intersects(ball.getNode(), criticalRegion)) {
            Paint ballColor = ((Circle) ball.getNode()).getFill();
            return !ballColor.equals(middleColor);
        }

        return false;
    }

    @Override
    public void stop() {
        quarterTimer.cancel();
    }

    GearsObstacle(Node node) {
        super(node, 10000);

        this.criticalRegion = (Rectangle) components.get(0).getNode();

        Group rings = (Group) components.get(1).getNode();
        this.leftGear = (Group) rings.getChildren().get(0);
        this.rightGear = (Group) rings.getChildren().get(1);

        this.colors = new ArrayList<Paint>();
        for (Node arc : leftGear.getChildren()) {
            colors.add(((Arc) arc).getStroke());
        }

        this.quarterTimer = new Timer();
    }
}

class ObstacleComponent {
    private Obstacle obstacle;
    private Paint color;
    private Node node;

    public void setColor(Paint color) {
        this.color = color;
    }

    public Paint getColor() {
        return this.color;
    }

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    ObstacleComponent(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
