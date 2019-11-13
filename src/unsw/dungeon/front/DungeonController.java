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
import javafx.scene.control.Label;
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
import unsw.dungeon.back.Texture;
import unsw.dungeon.back.event.CellHitWithSwordEvent;
import unsw.dungeon.back.event.CellRedrawEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class DungeonController implements Observer {
	@FXML
	private GridPane squares;
	
    @FXML
    private Label statusPotionDuration;
    
    @FXML
    private Label statusSwordDurability;
	
    @FXML
    private ImageView statusKeyIcon;
    
	
	/**
	 *  entityGroup[x][y] is a group of ImageViews displayed in the (x, y)
	 *  node of the GridPane.
	 */
	private ArrayList<ArrayList<Group>> entityGroup;
	private ArrayList<ArrayList<Group>> effectGroup;

	private Game game;

	/**
	 * Used to cache images by their URL.
	 */
	private Map<String, Image> imageCache;
	
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

		this.imageCache = new HashMap<String, Image>();
	}

	@FXML
	public void initialize() {
		this.startEnemyTimeline();
		this.startBuffTimeline();
		
		
		this.entityGroup = new ArrayList<ArrayList<Group>>();
		this.effectGroup = new ArrayList<ArrayList<Group>>();
		for (int x = 0; x < this.game.getWidth(); ++x) {
			this.entityGroup.add(new ArrayList<Group>());
			this.effectGroup.add(new ArrayList<Group>());
			for (int y = 0; y < this.game.getHeight(); ++y) {
				Group entityGroup = new Group();
				this.entityGroup.get(x).add(entityGroup);
				squares.add(entityGroup, x, y);
				
				Group effectGroup = new Group();
				this.effectGroup.get(x).add(effectGroup);
				squares.add(effectGroup, x, y);
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
		
		for (Texture texture : cell.getTextures()) {
			ImageView view = new ImageView(this.getImage(texture));			this.entityGroup.get(x).get(y).getChildren().add(view);
		}
	}

	private void playSwordAnimation(Cell cell) {
		int x = cell.getX();
		int y = cell.getY();
		this.effectGroup.get(x).get(y).getChildren().add(new ImageView("hound.png"));
	}
	
	private Image getImage(Texture texture) {
		String src = texture.getImageSrc();
		if (this.imageCache.get(src) == null) {
			this.imageCache.put(src, new Image(src));
		}
		return this.imageCache.get(src); 
		
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
		} else if (event instanceof CellHitWithSwordEvent) {
			Cell cell = ((CellHitWithSwordEvent) event).getCell();
			this.playSwordAnimation(cell);
		}
	}
}

