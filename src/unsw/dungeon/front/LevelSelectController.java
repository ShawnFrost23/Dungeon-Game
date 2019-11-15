package unsw.dungeon.front;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
		Path dir = Paths.get("dungeons");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
			this.levelsGroup = new ToggleGroup();
			
		    for (Path file : stream) {
		        System.out.println(file);
		        
		        RadioButton rb = new RadioButton(file.toString());
		        rb.setToggleGroup(this.levelsGroup);
		        levelList.getChildren().add(rb);
		        
		        rb.setOnAction((ActionEvent e) -> {
		        	this.playBtn.setDisable(false);
		        	this.selectedLevelPath = file.toString();
		        });
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
		// If there are no levels, display a message ... 
	}
	
	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
		
	}

	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}
	
	@FXML
	public void handleMenuBtn(ActionEvent event) {
		// System.out.println(levelSelectRadio.getSelectedToggle().selectedProperty());
		
		// this.startScreen.start();
	}
	
	@FXML
	public void handlePlayBtn(ActionEvent event) {
		this.dungeonScreen.loadLevel(this.selectedLevelPath);
	}
}
