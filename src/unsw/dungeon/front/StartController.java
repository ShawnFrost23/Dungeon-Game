package unsw.dungeon.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

	@FXML
	private Button startBtn;
	
	@FXML
	private Button levelSelectBtn;

	private LevelSelectScreen levelSelectScreen;
	
	public void setLevelSelectScreen(LevelSelectScreen levelSelectScreen) {
		this.levelSelectScreen = levelSelectScreen;
	}
	@FXML
	public void handleLevelSelectBtn(ActionEvent event) {
		this.levelSelectScreen.start();
	}
}

