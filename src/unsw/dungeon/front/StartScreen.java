package unsw.dungeon.front;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartScreen {

	private Stage stage;
	private String title;
	private StartController controller;
	private Scene scene;

	public StartScreen(Stage stage) throws IOException {
		this.stage = stage;
		this.title = "Start Screen";

		this.controller = new StartController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StartView.fxml"));
		loader.setController(controller);

		Parent root = loader.load();
		this.scene = new Scene(root);
		root.requestFocus();
	}

	public void start() {
		stage.setTitle(title);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
	}

	public StartController getController() {
		return this.controller;
	}
}
