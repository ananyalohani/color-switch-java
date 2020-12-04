import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.Group;
import java.io.*;

public class HelpController {
    private StartMenu startMenu;

    @FXML
    private Group backBtn;

    @FXML
    private void backBtnClicked() {
        startMenu.display();
    }

    public void setup(StartMenu startMenu) {
        this.startMenu = startMenu;
    }
}
