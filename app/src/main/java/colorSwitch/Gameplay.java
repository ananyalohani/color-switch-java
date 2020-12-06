import java.io.*;
import java.util.*;
import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gameplay implements IScene {
    // Constants
    private static transient Integer count = 1;

    // State and variables
    private boolean paused = false;
    private Stage stage;
    private Scene scene;

    // Menus and state
    private GameState currentState;
    private PauseMenu pauseMenu;
    private EndMenu endMenu;
    private GameplayController gpController;
    private AnimationTimer renderLoop;

    public Gameplay(SavedGame savedGame, Stage stage) {
        commonSetup(stage);
        // DO GAMESTATE THING HERE
        // deserialize(savedGame.getGameStateFile());
    }

    public Gameplay(Stage stage) {
        commonSetup(stage);
        this.currentState = new GameState(this);
    }

    // Setting menus
    private void commonSetup(Stage stage) {
        this.stage = stage;

        pauseMenu = new PauseMenu(this);
        endMenu = new EndMenu(this);

        renderLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                currentState.updateState(now);
            }
        };
    }

    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Scene.GAMEPLAY));
            scene = new Scene(loader.load());
            stage.setScene(scene);
            gpController = loader.getController();
        } catch(IOException e) {
            e.printStackTrace();
        }

        currentState.setup();
    }

    public GameState getState() {
        return this.currentState;
    }

    public Scene getScene() {
        return this.scene;
    }

    public Stage getStage() {
        return this.stage;
    }

    public GameplayController getController() {
        return this.gpController;
    }

    public Boolean getPaused() {
        return this.paused;
    }

    public void playGame(Boolean initialPlay) {
        paused = false;
        renderLoop.start();
        currentState.playState(initialPlay);
    }

    public void pauseGame() {
        paused = true;
        renderLoop.stop();
        currentState.pauseState();
        pauseMenu.display();
    }

    public void saveGame() {
        String label = "Game " + count.toString();
        count++;
        SavedGame savedGame = new SavedGame(currentState, label);

    }

    public void endGame() {
        // Stop all transitions and timers
        for (Obstacle obstacle : currentState.getObstacles()) {
            obstacle.stop();
        }

        App.game.setTotalScore(App.game.getTotalScore() + currentState.getScore());
        App.game.serialize();

        endMenu.display();
    }

    // public void serialize() {
    //     ObjectOutputStream out = null;
    //     try {
    //         out = new ObjectOutputStream(new FileOutputStream("file"));
    //         out.writeObject(currentState);
    //     } catch(IOException e) {
    //         System.out.println("error in serializing");
    //     } finally {
    //         out.close();
    //     }
    // }

    // public void deserialize(String filename) {
    //     ObjectInputStream in = null;
    //     try {
    //         in = new ObjectInputStream(new FileInputStream(filename));
    //         currentState = (GameState) in.readObject();
    //     } catch(IOException e) {
    //         System.out.println("error in deserializing");
    //     } finally {
    //         in.close();
    //     }
    // }
}
