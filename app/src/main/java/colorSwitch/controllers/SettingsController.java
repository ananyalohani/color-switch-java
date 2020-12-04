import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.Group;

public class SettingsController {
    private StartMenu startMenu;

    @FXML
    private Button musicBtn;
    
    @FXML
    private Group backBtn;


    public SettingsController() {}

    public void setup(StartMenu startMenu) {
        this.startMenu = startMenu;
    }

    @FXML
    private void musicBtnClicked() {
        String prevState = musicBtn.getText();
        if(prevState.equals("ON")) {
            String color = "#f5b5b9";
            musicBtn.setStyle("-fx-background-color:" + color + "; -fx-background-radius: 10;");
            musicBtn.setText("OFF");
            // * call function to turn music off
        } else  {
            String color = "#ace890";
            musicBtn.setStyle("-fx-background-color:" + color + "; -fx-background-radius: 10;");
            musicBtn.setText("ON");
            // * call function to turn music on
        }
    }

    @FXML
    private void backBtnClicked() {
        startMenu.display();
    }
}
