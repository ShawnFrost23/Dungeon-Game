package unsw.dungeon.front;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.back.Game;
import unsw.dungeon.back.PuzzleGoal;

public class DungeonApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
        StartScreen startScreen = new StartScreen(primaryStage);
        DungeonScreen dungeonScreen = new DungeonScreen(primaryStage);
		
        startScreen.getController().setDungeonScreen(dungeonScreen);
        dungeonScreen.getController().setStartScreen(startScreen);
        
        startScreen.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
