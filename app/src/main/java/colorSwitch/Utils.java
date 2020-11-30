import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;

public class Utils {
    public static RotateTransition rotate(Node node, int duration, int angle) {
        RotateTransition rt = new RotateTransition(Duration.millis(duration), node);
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

    public static Node loadObject(String file) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(Utils.class.getResource(file));
            return root.getChildren().get(0);
        } catch(Exception e) {
            return null;
        }
    }
}
