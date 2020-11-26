import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;

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
}
