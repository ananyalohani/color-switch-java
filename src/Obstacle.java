package src;
import java.util.ArrayList;

public abstract class Obstacle extends GameObject {
    protected ObstacleShape shape;
    protected Double velocity;
    private ArrayList<ObstacleComponent> components;
}

class CircleObstacle extends Obstacle {

}

class BarObstacle extends Obstacle {

}

class SquareObstacle extends Obstacle {

}

class TriangleObstacle extends Obstacle {
    
}

class ObstacleComponent {
    
}
