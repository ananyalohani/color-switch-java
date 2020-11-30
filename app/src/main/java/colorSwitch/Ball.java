import javafx.scene.shape.Circle;

public class Ball extends GameObject {
    private static final transient int JUMP_SIZE = 100; // pixels
    private static final transient int JUMP_DURATION = 200; // milliseconds
    private static final transient double VELOCITY = Physics.velocity(JUMP_SIZE, JUMP_DURATION);
    private static final transient double DOWNWARD_ACCN = Physics.acceleration(VELOCITY, JUMP_SIZE);

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

    public void setNode(Circle node) {
        this.node = node;
    }

    public double move(double duration, double translateOffset) {
        double displacement = Physics.displacement(VELOCITY, duration, DOWNWARD_ACCN);
        double resolvedDisplacement = displacement - translateOffset;
        node.setTranslateY(-resolvedDisplacement > 0 ? 0 : -resolvedDisplacement);
        return resolvedDisplacement;
    }
}
