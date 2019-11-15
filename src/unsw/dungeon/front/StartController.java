package unsw.dungeon.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

	@FXML
	private Button startBtn;
	
	@FXML
	private Button levelSelectBtn;

	private DungeonScreen dungeonScreen;
	private LevelSelectScreen levelSelectScreen;
	
	public void setLevelSelectScreen(LevelSelectScreen levelSelectScreen) {
		this.levelSelectScreen = levelSelectScreen;
	}
	
	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}
	
	@FXML
	public void handleStartBtn(ActionEvent event) {
		this.dungeonScreen.start();
	}
	
	@FXML
	public void handleLevelSelectBtn(ActionEvent event) {
		this.levelSelectScreen.start();
	}
}

