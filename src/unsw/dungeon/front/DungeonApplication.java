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
		primaryStage.setTitle("Dungeon");

		Game game = Game.createGame(new PuzzleGoal(), ""
			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n"
			+ "W    T                 W  !    T W\n"
			+ "W  T  W  BWW    ! WWW  W  W B    W\n"
			+ "W     ~   SW      W_W  W WWO     W\n"
			+ "W  P  B _              W  W      W\n"
			+ "W  S  *    # ! _   O   W    W    W\n"
			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n"
			, ""
			+ "                                  \n"
			+ "                                  \n"
			+ "                                  \n"
			+ "                   ~    #         \n"
			+ "                                  \n"
			+ "                                  \n"
			+ "                                  \n"
		);
		// Game game = Game.createGame("blah.json")
		
		DungeonController controller = new DungeonController(game);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
		loader.setController(controller);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		root.requestFocus();
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
