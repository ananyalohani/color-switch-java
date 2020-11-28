import javafx.scene.shape.Circle;

public class Ball extends GameObject {
    private final transient double DOWNWARD_ACCN;
    private final transient double VELOCITY;
    private Color ballColor;
    private Circle node;

    public Color getBallColor() {
        return this.ballColor;
    }

    public void setBallColor(Color newColor) {
        this.ballColor = newColor;
    }

    public Circle getNode() {
        return this.node;
    }

    public double move(double duration, double translateOffset) {
        double displacement = Physics.displacement(VELOCITY, duration, DOWNWARD_ACCN);
        double resolvedDisplacement = displacement - translateOffset;
        node.setTranslateY(-resolvedDisplacement);
        return resolvedDisplacement;
    }

    Ball(Circle node, double velocity, double accn) {
        this.node = node;
        this.DOWNWARD_ACCN = accn;
        this.VELOCITY = velocity;
    }
}
