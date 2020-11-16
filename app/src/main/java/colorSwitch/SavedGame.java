import java.io.Serializable;
import java.util.Date;

public class SavedGame implements Serializable {
    private Integer id;
    private String label;
    private String gameStateFile;
    private Date timestamp;
    private static Integer lastSavedGameId;

    SavedGame(GameState state, String label) {
        
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getGameStateFile() {
        return this.gameStateFile;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}
