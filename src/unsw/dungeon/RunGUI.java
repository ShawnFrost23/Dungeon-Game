package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.back.Game;
import unsw.dungeon.back.PuzzleGoal;
import unsw.dungeon.front.DungeonScreen;
import unsw.dungeon.front.LevelSelectScreen;
import unsw.dungeon.front.StartScreen;

public class RunGUI extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
        StartScreen startScreen = new StartScreen(primaryStage);
        LevelSelectScreen levelSelectScreen = new LevelSelectScreen(primaryStage);
        DungeonScreen dungeonScreen = new DungeonScreen(primaryStage);
		
        startScreen.getController().setLevelSelectScreen(levelSelectScreen);
        startScreen.getController().setDungeonScreen(dungeonScreen);
        dungeonScreen.getController().setStartScreen(startScreen);
        
        
        levelSelectScreen.getController().setStartScreen(startScreen);
        levelSelectScreen.getController().setDungeonScreen(dungeonScreen);
        
        
        startScreen.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
