import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class EndMenu extends Menu {
    private Stage stage;

    @Override
    public void display() {
        displayMenu();
    }

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLs.Scene.END_MENU));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            EndMenuController emController = loader.getController();
            emController.setup(this);
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

    public void newGame() {
        StartMenu startMenu = new StartMenu(null);
        startMenu.setStage(stage);
        startMenu.newGame();
    }

    public void saveToLeaderboard() {

    }

    EndMenu(Gameplay game) {
        super(game);
        this.stage = game.getStage();
    }
}
