package unsw.dungeon.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Group;
import javafx.util.Duration;
import unsw.dungeon.back.Board;
import unsw.dungeon.back.Cell;
import unsw.dungeon.back.Direction;
import unsw.dungeon.back.Game;
import unsw.dungeon.back.event.CellRedrawEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class DungeonController implements Observer {
	@FXML
	private GridPane squares;
	
	/**
	 *  entityGroup[x][y] is a group of ImageViews displayed in the (x, y)
	 *  node of the GridPane.
	 *  We keep track of this because the GridPane only supports a
	 *  get-image-by-coordinates function that is linear in the number of images
	 *  drawn.
	 */
	private ArrayList<ArrayList<Group>> entityGroup;

	private Game game;

	private Map<Character, Image> images;
	
	private Timeline enemyTimeline;
	private Timeline buffTimeline;

	public DungeonController(Game game) {
		this.game = game;

		final KeyFrame kfEnemies = new KeyFrame(Duration.millis(500), 
			(ActionEvent event) -> {
				this.game.moveEnemies();
			}
		);
		this.enemyTimeline = new Timeline();
		this.enemyTimeline.getKeyFrames().add(kfEnemies);
		this.enemyTimeline.setCycleCount(Timeline.INDEFINITE);
		
		final KeyFrame kfBuffs = new KeyFrame(Duration.millis(1000), 
			(ActionEvent event) -> {
				this.game.tickBuffs();
			}
		);
		this.buffTimeline = new Timeline();
		this.buffTimeline.getKeyFrames().add(kfBuffs);
		this.buffTimeline.setCycleCount(Timeline.INDEFINITE);
		
		
		this.images = new HashMap<Character, Image>();
		images.put('B', new Image("boulder.png"));
		images.put('W', new Image("brick_brown_0.png"));
		images.put('*', new Image("brilliant_blue_new.png"));
		// images.put(' ', new Image("bubbly.png"));
		images.put('#', new Image("closed_door.png"));
		images.put('!', new Image("deep_elf_master_archer.png"));
		images.put(' ', new Image("dirt_0_new.png"));
		images.put('E', new Image("exit.png"));
		// images.put(' ', new Image("gnome.png");
		images.put('T', new Image("gold_pile.png"));
		images.put('S', new Image("greatsword_1_new.png"));
		// images.put(' ', new Image("hound.png");
		images.put('P', new Image("human_new.png"));
		images.put('~', new Image("key.png"));
		images.put('|', new Image("open_door.png"));
		images.put('O', new Image("portal.png"));
		images.put('_', new Image("pressure_plate.png"));
	}

	@FXML
	public void initialize() {
		this.startEnemyTimeline();
		this.startBuffTimeline();

		this.entityGroup = new ArrayList<ArrayList<Group>>();
		for (int x = 0; x < this.game.getWidth(); ++x) {
			this.entityGroup.add(new ArrayList<Group>());
			for (int y = 0; y < this.game.getHeight(); ++y) {
				Group group = new Group();
				this.entityGroup.get(x).add(group);
				squares.add(group, x, y);
			}
		}
		
		for (Cell cell : this.game.getCells()) {
			cell.attachListener(this);
			this.redraw(cell);
		}
		
	}
	
	private void redraw(Cell cell) {
		int x = cell.getX();
		int y = cell.getY();

		this.entityGroup.get(x).get(y).getChildren().clear();
		
		for (Character c : cell.getTextures()) {
			ImageView view = new ImageView(this.images.get(c));
			this.entityGroup.get(x).get(y).getChildren().add(view);
		}
	}

	private void startEnemyTimeline() {
		this.enemyTimeline.play();
	}
	
	private void startBuffTimeline() {
		this.buffTimeline.play();
	}

	@FXML
	public void handleKeyPress(KeyEvent event) {
		switch (event.getCode()) {
		case UP:
			game.swingSword(Direction.UP);
			break;
		case DOWN:
			game.swingSword(Direction.DOWN);
			break;
		case LEFT:
			game.swingSword(Direction.LEFT);
			break;
		case RIGHT:
			game.swingSword(Direction.RIGHT);
			break;
		case W:
			game.movePlayer(Direction.UP);
			break;
		case S:
			game.movePlayer(Direction.DOWN);
			break;
		case A:
			game.movePlayer(Direction.LEFT);
			break;
		case D:
			game.movePlayer(Direction.RIGHT);
			break;
		case Q:
			game.swapKey();
			break;
		default:
			break;
		}
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellRedrawEvent) {
			Cell cell = ((CellRedrawEvent) event).getCell();
			this.redraw(cell);
		}
	}
}

