import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class EndMenu extends Menu {
    private Stage stage;
    private Text scoreCountNode;

    @Override
    public void display() {
        displayMenu();
    }

    @Override
    public void displayMenu() {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Scene.END_MENU));

            scene = new Scene(loader.load());
            stage.setScene(scene);

            EndMenuController emController = loader.getController();
            emController.setup(this);
        } catch(IOException e) {
            e.printStackTrace();
        }

        AnchorPane anchorPane = (AnchorPane) ((VBox) scene.getRoot()).getChildren().get(0);
        FadeTransition transition = new FadeTransition(Duration.millis(500), anchorPane);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();

        scoreCountNode.setText(game.getState().getScore().toString());
    }

    @Override
    public void exit() {
        StartMenu startMenu = new StartMenu();
        startMenu.setStage(stage);
        startMenu.display();
    }

    public void initNodes(Text scoreCountNode) {
        this.scoreCountNode = scoreCountNode;
    }

    public void newGame() {
        StartMenu startMenu = new StartMenu();
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
