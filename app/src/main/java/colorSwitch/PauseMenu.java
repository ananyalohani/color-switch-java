import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.stage.Stage;

public class PauseMenu extends Menu{
    @Override
    public void displayMenu(Stage stage) {

    }

    @Override
    public void exit() {
        
    }

    public void resumeGame() {

    }

    public void saveGame() {

    }

    PauseMenu(Gameplay game) {
        super(game);
    }
}
