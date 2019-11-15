package unsw.dungeon.front;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class LevelSelectController {
	
	@FXML
	private Button menuBtn;
	
	@FXML
	private VBox levelList;
	
	@FXML
	private Button playBtn;
	
	private DungeonScreen dungeonScreen;
	private StartScreen startScreen;
	
	private String selectedLevelPath;
	private ToggleGroup levelsGroup;

	public void displayAvailableLevels() {
		this.playBtn.setDisable(true);
		
		String path = "dungeons";
		Path dir = Paths.get(path);
		int numLevels = 0;
		DirectoryStream<Path> stream;
		try {
			stream = Files.newDirectoryStream(dir, "*.json");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Could not load dungeon list.");
		}

		levelList.getChildren().clear();
		
		this.levelsGroup = new ToggleGroup();
		
	    for (Path file : stream) {
	    	numLevels += 1;
        
        	String displayString = file.getFileName().toString();
        	displayString = displayString.replaceFirst(".json$", "");
	        
	        RadioButton rb = new RadioButton(displayString);
	        rb.setToggleGroup(this.levelsGroup);
	        levelList.getChildren().add(rb);
	        
	        rb.setOnAction((ActionEvent e) -> {
	        	this.playBtn.setDisable(false);
	        	this.selectedLevelPath = file.toString();
	        });
	    }

		if (numLevels == 0) {
			levelList.getChildren().add(new Label("Could not find levels."));
		}
	}
	
	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
		
	}

	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}
	
	@FXML
	public void handleMenuBtn(ActionEvent event) {
		this.startScreen.start();
	}
	
	@FXML
	public void handlePlayBtn(ActionEvent event) {
		this.dungeonScreen.loadLevel(this.selectedLevelPath);
	}
}
