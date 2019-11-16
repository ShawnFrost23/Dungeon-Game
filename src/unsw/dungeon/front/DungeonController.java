package unsw.dungeon.front;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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
import unsw.dungeon.back.event.GameOverEvent;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PlayerKilledEvent;
import unsw.dungeon.back.event.CellKeyDroppedEvent;

public class DungeonController implements Observer {
	@FXML
	private GridPane squares;
	
	@FXML
	private Label statusPotionDuration;
	
	@FXML
	private Label statusSwordDurability;
	
	@FXML
	private ImageView statusKeyIcon;
	
	@FXML
	private StackPane pauseMenu;
	
	@FXML
	private StackPane winMenu;
	
	@FXML
	private StackPane loseMenu;
	
	@FXML
	private Button resumeBtn;
	
	@FXML
	private Button restartBtn;
	
	@FXML
	private Button backToMenuBtn;
	
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
	
	private StartScreen startScreen;

	private boolean preventPlayerMovement;

	private boolean isPaused;

	private String jsonPath;

	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}
	
	public DungeonController() {

	}

	// don't forget to call this when navigating away!
	private void unloadGame() {
		if (this.game == null) {
			return;
		}
		
		this.squares.getChildren().clear();
		this.game.detachListener(this);
		
		// TODO: also unload bindings?
		// "Note that JavaFX has all the bind calls implemented through weak listeners.
		// This means the bound property can be garbage collected and stopped from being
		// updated."
	}
	
	public void loadGame(String jsonPath) {
		this.jsonPath = jsonPath;
		try {
			this.game = Game.createGame(jsonPath);
		} catch (FileNotFoundException e) {
			throw new Error("" + jsonPath + " file not found.");
		}
		
		this.preventPlayerMovement = true;
		this.game.attachListener(this);
		this.isPaused = false;
		this.winMenu.setVisible(false);
		this.pauseMenu.setVisible(false);
		this.loseMenu.setVisible(false);

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
		
		this.statusPotionDuration.textProperty().bind(Bindings.convert(game.getPotionDurProperty()));
		this.statusSwordDurability.textProperty().bind(Bindings.convert(game.getSwordDurProperty()));
	}

	@FXML
	public void initialize() {
	}
	
	private void redraw(Cell cell) {
		int x = cell.getX();
		int y = cell.getY();

		this.entityGroup.get(x).get(y).getChildren().clear();
		
		for (Texture texture : cell.getTextures()) {
			ImageView view = new ImageView(this.getImage(texture));			this.entityGroup.get(x).get(y).getChildren().add(view);
		}
	}

	private void playKeyAnimation(Cell cell) {
		int x = cell.getX();
		int y = cell.getY();
		ImageView keyPickup = new ImageView();
		Timeline keyAnimationTimeline = new Timeline();
		keyAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), 
			(ActionEvent event) -> {
				this.effectGroup.get(x).get(y).getChildren().add(keyPickup);
				keyPickup.setImage(this.getImage(new Texture('?', "key_drop_1.png")));
			}
		));
		
		keyAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), 
			(ActionEvent event) -> {
				keyPickup.setImage(this.getImage(new Texture('?', "key_drop_2.png")));
			}
		));
		
		keyAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), 
			(ActionEvent event) -> {
				keyPickup.setImage(this.getImage(new Texture('?', "key_drop_3.png")));
			}
		));
		
		keyAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), 
			(ActionEvent event) -> {
				this.effectGroup.get(x).get(y).getChildren().remove(keyPickup);
			}
		));
		
		keyAnimationTimeline.play();
	}
	
	private void playSwordAnimation(Cell cell, Direction d) {
		int x = cell.getX();
		int y = cell.getY();
		ImageView swordSwing = new ImageView();
		if (d == Direction.UP) {
			swordSwing.setRotate(270);
		} else if (d == Direction.LEFT) {
			swordSwing.setRotate(180);
		} else if (d == Direction.DOWN) {
			swordSwing.setRotate(90);
		} else if (d == Direction.RIGHT) {
			swordSwing.setRotate(0);
		}
		
		Timeline swordAnimationTimeline = new Timeline();
		
		swordAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), 
			(ActionEvent event) -> {
				this.effectGroup.get(x).get(y).getChildren().add(swordSwing);
				swordSwing.setImage(this.getImage(new Texture('?', "sword_swing_1.png")));
			}
		));
		
		swordAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), 
			(ActionEvent event) -> {
				swordSwing.setOpacity(0.6);
				swordSwing.setImage(this.getImage(new Texture('?', "sword_swing_2.png")));
			}
		));
		
		swordAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), 
			(ActionEvent event) -> {
				swordSwing.setOpacity(0.3);
				swordSwing.setImage(this.getImage(new Texture('?', "sword_swing_3.png")));
			}
		));
		
		swordAnimationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), 
			(ActionEvent event) -> {
				this.effectGroup.get(x).get(y).getChildren().remove(swordSwing);
			}
		));
		
		swordAnimationTimeline.play();
	}
	
	private Image getImage(Texture texture) {
		String src = texture.getImageSrc();
		if (this.imageCache.get(src) == null) {
			this.imageCache.put(src, new Image(src));
		}
		return this.imageCache.get(src); 
		
	}
	
	/**
	 * Warning: this will not pause ongoing sword animations.
	 */
	public void pause() {
		this.enemyTimeline.pause();
		this.buffTimeline.pause();
		this.preventPlayerMovement = true;
	}
	public void play() {
		this.enemyTimeline.play();
		this.buffTimeline.play();
		this.preventPlayerMovement = false;
	}

	@FXML
	public void handleKeyPress(KeyEvent event) {
		switch (event.getCode()) {
		// TODO: make sure winning / losing prevents all key presses ...
		case ESCAPE:
			this.togglePauseMenu();
			break;
		default:
			break;
		}
		
		if (this.preventPlayerMovement) {
			return;
		}
		
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

	private void togglePauseMenu() {
		if (this.isPaused) {
			this.isPaused = false;
			this.play();
			this.pauseMenu.setVisible(false);
			squares.requestFocus();
		} else {
			this.isPaused = true;
			this.pause();
			this.pauseMenu.setVisible(true);
		}
	}
	
	private void toggleWinMenu() {
		this.winMenu.setVisible(true);
	}
	
	private void toggleLoseMenu() {
		this.loseMenu.setVisible(true);
	}
	
	@FXML
	public void handleResumeBtn(ActionEvent event) {
		this.togglePauseMenu();
	}
	
	@FXML
	public void handleRestartBtn(ActionEvent event) {
		this.togglePauseMenu();
		this.pause();
		this.unloadGame();
		this.loadGame(this.jsonPath);
		this.play();
	}
	
	@FXML
	public void handleRestartBtnW(ActionEvent event) {
		this.isPaused = true;
		this.togglePauseMenu();
		this.pause();
		this.unloadGame();
		this.loadGame(this.jsonPath);
		this.play();
	}
	
	// timers aren't properly unbound ... wah.
	
	@FXML
	public void handleBackToMenuBtn(ActionEvent event) {
		this.togglePauseMenu();
		this.pause();
		this.pauseMenu.setVisible(false);
		this.unloadGame();
		this.startScreen.start();
	}
	
	@FXML
	public void handleBackToMenuBtnW(ActionEvent event) {	
		this.isPaused = true;
		this.togglePauseMenu();
		this.pause();
		this.winMenu.setVisible(false);
		this.loseMenu.setVisible(false);
		this.unloadGame();
		this.startScreen.start();
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellRedrawEvent) {
			Cell cell = ((CellRedrawEvent) event).getCell();
			this.redraw(cell);
		} else if (event instanceof CellHitWithSwordEvent) {
			Cell cell = ((CellHitWithSwordEvent) event).getCell();
			Direction d = ((CellHitWithSwordEvent) event).getDirection();
			this.playSwordAnimation(cell, d);
		} else if (event instanceof GameOverEvent) {
			if (this.game.getHasWon()) {
				System.out.println("win");
				this.toggleWinMenu();
				this.pause();
			} else if (this.game.getHasLost()) {
				System.out.println("lose");
				this.toggleLoseMenu();
				this.pause();
			}
		} else if (event instanceof CellKeyDroppedEvent) {
			Cell cell = ((CellKeyDroppedEvent) event).getCell();			
			this.playKeyAnimation(cell);
		}
	}
	
	
	
}

