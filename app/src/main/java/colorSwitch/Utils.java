import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.geometry.Bounds;
import javafx.collections.*;
import java.util.*;
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

    public static Bounds getBounds(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    public static Boolean intersects(Node a, Node b) {
        Bounds boundsA = getBounds(a);
        Bounds boundsB = getBounds(b);
        return intersects(boundsA, boundsB);
    }

    public static Boolean intersects(Bounds a, Bounds b) {
        return a.intersects(b) && b.intersects(a);
    }

    public static Boolean isInside(Node a, Node b) {
        Bounds boundsA = getBounds(a);
        Bounds boundsB = getBounds(b);
        return isInside(boundsA, boundsB);
    }

    public static Boolean isInside(Bounds a, Bounds b) {
        return a.getMinY() > b.getMinY() && a.getMaxY() < b.getMaxY();
    }

    public static Node loadObject(String file) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(Utils.class.getResource(file));
            return root.getChildren().get(0);
        } catch(Exception e) {
            return null;
        }
    }

    public static ArrayList<ObstacleComponent> getComponents(
        Obstacle obstacle,
        ObservableList<Node> children
    ) {
        ArrayList<ObstacleComponent> components = new ArrayList<ObstacleComponent>();
        for (Node node : children) {
            ObstacleComponent obsComp = new ObstacleComponent(obstacle);
            obsComp.setNode(node);
            components.add(obsComp);
        }
        return components;
    }

    public static void deleteNode(Node node) {
        ((Group) node.getParent()).getChildren().remove(node);
    }
}
