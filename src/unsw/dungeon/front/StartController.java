package unsw.dungeon.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController {

	@FXML
	private Button quitBtn;
	
	@FXML
	private Button levelSelectBtn;

	private LevelSelectScreen levelSelectScreen;

	private Stage stage;
	
	public StartController(Stage stage) {
		this.stage = stage;
	}
	public void setLevelSelectScreen(LevelSelectScreen levelSelectScreen) {
		this.levelSelectScreen = levelSelectScreen;
	}
	@FXML
	public void handleLevelSelectBtn(ActionEvent event) {
		this.levelSelectScreen.start();
	}
	
	@FXML
	public void handleQuitBtn(ActionEvent event) {
		this.stage.close();
	}	
}

