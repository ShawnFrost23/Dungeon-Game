package unsw.dungeon.front;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.dungeon.back.Game;
import unsw.dungeon.back.PuzzleGoal;

public class DungeonScreen {

    private Stage stage;
    private String title;
    private DungeonController controller;
    private Scene scene;
    
    public DungeonScreen(Stage stage) throws IOException {
    	this.stage = stage;
    	this.title = "Dungeon Screen";

    	Game game = Game.createGame(new PuzzleGoal(), ""
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n"
    			+ "W    T                 W       T           W\n"
    			+ "W  T  W  BWW      WWW  W  W B              W\n"
    			+ "W     ~   SW   !  W_W  W WWO               W\n"
    			+ "W  P  B _   !          W  W         !      W\n"
    			+ "W  S  * !  #   _   O   W    W              W\n"
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n"
    			, ""
    			+ "                                            \n"
    			+ "                                            \n"
    			+ "                                            \n"
    			+ "                   ~    #                   \n"
    			+ "                                            \n"
    			+ "                                            \n"
    			+ "                                            \n"
    		);
    	
    	controller = new DungeonController(game);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
    	loader.setController(controller);
    	Parent root = loader.load();
    	this.scene = new Scene(root);
    	root.requestFocus();
    }
    
    public void start() {
    	// TODO: move logic from the constructor into this -- start
    	// should take in a game and set up a controller appropriately,
    	// not have it fixed at construction time.
    	this.stage.setTitle(this.title);
    	this.stage.setScene(this.scene);
    	this.stage.show();
    }
    public DungeonController getController() {
    	return this.controller;
    }
}


//primaryStage.setTitle("Dungeon");
//

//// Game game = Game.createGame("blah.json")
//
//DungeonController controller = new DungeonController(game);
//
//FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
//loader.setController(controller);
//Parent root = loader.load();
//Scene scene = new Scene(root);
//root.requestFocus();
//primaryStage.setResizable(false);
//primaryStage.setScene(scene);
//primaryStage.show();