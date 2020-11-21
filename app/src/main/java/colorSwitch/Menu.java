import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.stage.Stage;

public abstract class Menu {
    protected Gameplay game;

    Menu(Gameplay game) {
        this.game = game;
    }

    public abstract void displayMenu(Stage stage);
    public abstract void exit();
}