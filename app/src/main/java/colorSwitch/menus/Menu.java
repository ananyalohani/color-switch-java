import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.stage.Stage;

public abstract class Menu implements IScene {
    protected Gameplay game;
    protected Stage stage;

    Menu(Gameplay game) {
        this.game = game;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public abstract void displayMenu();
    public abstract void exit();
}
