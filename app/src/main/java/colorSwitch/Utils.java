import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.geometry.Bounds;

public class Utils {
    public static RotateTransition rotate(Group c, int duration, int angle) {
        RotateTransition rt = new RotateTransition(Duration.millis(duration), c);
        rt.setByAngle(angle);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        return rt;
    }

    public static double getAbsoluteY(Node node) {
        return node.getTranslateY() + node.getLayoutY();
    }

    public static Boolean intersects(Node a, Node b) {
        Bounds boundsA = a.localToScene(a.getBoundsInLocal());
        Bounds boundsB = b.localToScene(b.getBoundsInLocal());
        return boundsA.intersects(boundsB) && boundsB.intersects(boundsA);
    }
}
