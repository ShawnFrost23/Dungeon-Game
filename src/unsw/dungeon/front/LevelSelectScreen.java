package unsw.dungeon.front;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LevelSelectScreen {

	private Stage stage;
	private String title;
	private LevelSelectController controller;
	private Scene scene;
	
	public LevelSelectScreen(Stage primaryStage) throws IOException {
		this.stage = primaryStage;
		this.title = "Level Select Screen";

		this.controller = new LevelSelectController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelSelectView.fxml"));
		loader.setController(controller);
		
		Parent root = loader.load();
		this.scene = new Scene(root);
		root.requestFocus();
	}

	public void start() {
		this.controller.displayAvailableLevels();
		
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}

	public LevelSelectController getController() {
		return this.controller;
	}
}
