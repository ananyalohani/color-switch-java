import java.util.ArrayList;

public abstract class Obstacle extends GameObject {
    public static final transient String CIRCLE = "game-objects/circleObstacle.fxml";
    public static final transient String TRIANGLE = "game-objects/triangleObstacle.fxml";
    public static final transient String SQUARE = "game-objects/squareObstacle.fxml";
    public static final transient String BAR = "game-objects/barObstacle.fxml";
    public static final transient String OBSTACLES[] = { CIRCLE, TRIANGLE, SQUARE, BAR };

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
}

class CircleObstacle extends Obstacle {
    CircleObstacle() {

    }
}

class BarObstacle extends Obstacle {
    BarObstacle() {

    }
}

class SquareObstacle extends Obstacle {
    SquareObstacle() {
        
    }
}

class TriangleObstacle extends Obstacle {
    TriangleObstacle() {

    }
}

class ObstacleComponent {
    private Obstacle obstacle;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
