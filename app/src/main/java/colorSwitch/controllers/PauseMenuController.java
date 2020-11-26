import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class PauseMenuController {
    private PauseMenu pauseMenu;

    @FXML
    private Text scoreCount;

    @FXML
    private Button mainMenuBtn, saveGameBtn, endGameBtn;

    @FXML
    private Group resumeBtn;

    public PauseMenuController() {}

    public void setup(PauseMenu pauseMenu) {
        this.pauseMenu = pauseMenu;
    }

    @FXML
    private void resumeBtnClicked() {
        pauseMenu.resumeGame();
    }

    @FXML
    public void saveGameBtnClicked() {
        pauseMenu.saveGame();
    }

    @FXML
    private void endGameBtnClicked() {
        pauseMenu.endGame();
    }

    @FXML
    private void mainMenuBtnClicked() {
        pauseMenu.exit();
    }
}
