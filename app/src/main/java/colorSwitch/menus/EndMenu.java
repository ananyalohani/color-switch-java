import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class EndMenu extends Menu {
    private Stage stage;

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/endMenu.fxml"));
            stage.setScene(new Scene(loader.load()));
            EndMenuController cont = loader.getController();
            cont.initData(this, stage);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() {
        StartMenu sm = new StartMenu(null);
        sm.setStage(stage);
        sm.displayMenu();
    }

    public void saveToLeaderboard() {

    }

    public void newGame() {
        StartMenu sm = new StartMenu(null);
        sm.setStage(stage);
        sm.newGame();
    }

    EndMenu(Gameplay game, Stage stage) {
        super(game);
        this.stage = stage;
    }
}
