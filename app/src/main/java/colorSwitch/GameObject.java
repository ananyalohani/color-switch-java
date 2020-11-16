import java.io.Serializable;

public abstract class GameObject implements Serializable {
    protected Point position;

    public void setPosition(Point point) {

    }

    public Point getPosition() {
        return this.position;
    }
}

class Star extends GameObject {
    private Integer value;

    public Integer getValue() {
        return this.value;
    }
}
