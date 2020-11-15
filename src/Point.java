package src;

public class Point {
    public Double x;
    public Double y;

    Point(double x, double y) {
        set(x, y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
