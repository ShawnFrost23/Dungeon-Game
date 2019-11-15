package unsw.dungeon.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

    @FXML
    private Button startButton;

    private DungeonScreen dungeonScreen;
    
    
    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }
    
    @FXML
    void handleStartButton(ActionEvent event) {
        this.dungeonScreen.start();
    }
}

