import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PauseMenu extends Menu {
    @Override
    public void display() {
        displayMenu();
    }

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/pauseMenu.fxml"));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            PauseMenuController pmController = loader.getController();
            pmController.setup(this);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() {
        StartMenu startMenu = new StartMenu(null);
        startMenu.setStage(stage);
        startMenu.display();
    }

    public void resumeGame() {
        stage.setScene(game.getScene());
        game.playGame();
    }

    public void saveGame() {
        game.saveGame();
    }

    public void endGame() {
        game.endGame();
    }

    PauseMenu(Gameplay game) {
        super(game);
    }
}
