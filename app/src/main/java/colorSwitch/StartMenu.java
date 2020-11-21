// import javafx.scene.Parent;
import javafx.scene.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;

public class StartMenu extends Menu {
    private ColorSwitch mainGame;

    @Override
    public void displayMenu(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scenes/startMenu.fxml"));
            stage.setScene(new Scene(root));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    public void viewLeaderboard() {

    }

    public void newGame() {

    }

    public void displaySavedGames() {

    }

    StartMenu(ColorSwitch game) {
        super(null);
        this.mainGame = game;
    }
}
