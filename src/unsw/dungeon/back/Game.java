package unsw.dungeon.back;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.back.event.EnemyKilledEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PlayerKilledEvent;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * The interface through which a Game can be played.
 */
public class Game implements Observer {
	private Board board;
	private Player player;
	private List<Enemy> enemies;
	private Goal goal;

	private boolean hasWon;
	private boolean hasLost;
	
	/**
	 * Enemies will try to make moves every 500ms ... if the player kills them
	 * between the time they decide their move and when they make it, there's
	 * an issue. We use this lock to restrict that only one player / enemy can
	 * move at once. 
	 */
	private Lock conurrentMovementLock;
	
	private Game(Goal goal) {
		this.hasWon = false;
		this.hasLost = false;
		this.enemies = new ArrayList<Enemy>();
		this.goal = goal;
		this.conurrentMovementLock = new ReentrantLock();
	}
	
	/**
	 * Create a mock game with predictable enemy movement for convenience of
	 * testing.
	 * @see {@link #createMockGame(Goal, String...)}
	 */
	public static Game createMockGame(Goal goal, String ...boardStrings) {
		Game game = Game.createGame(goal, boardStrings);
		for (Enemy e : game.enemies) {
			e.setMovementStrategy(new NaiveMovementStrategy());
		}
		return game;
	}

	/**
	 * Create a new Game object from a Board's string representation.
	 * <p>
	 * It is possible to place entities on top of one another by passing in a
	 * <b>boardStrings</b> argument with more than one String. For example, the
	 * following will create a Game that has a Boulder sitting on top of a
	 * FloorSwitch. 
	 * </p>
	 * 
	 * <code>
	 *   Game.createGame(
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" + 
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;P&nbsp;_\n" +
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" ,
	 *   <br /> 
	 *   &nbsp;&nbsp;
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" + 
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;&nbsp;&nbsp;B\n" +
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
	 *   <br />
	 *   );
	 * </code>
	 * <br />
	 * <br />
	 * If Portals are present in a <b>boardStrings</b> element, then there must
	 * be either exactly zero or exactly two of them per layer. Similarly, all
	 * Keys and Doors on a given layer will give them all the same ID.
	 * <br />
	 * <br />
	 * Games created with Boards that do not have Walls along all edges will
	 * fail in undefined ways if a Moveable attempts to move out of bounds, or
	 * a Player attempts to swing a sword out of bounds.
	 * <br />
	 * <br />
	 * @param goal Goal object representing the Game's win conditions
	 * @param boardStrings valid string representation of Entities on the board
	 * @return a Game object with entities as specified in <b>boardStrings</b>
	 */
	public static Game createGame(Goal goal, String ...boardStrings) {
		Game game = new Game(goal);
		game.board = Board.createBoard(game, goal, boardStrings);
		return game;
	}
	
	
	/**
	 * Create a game from a json file.
	 * @param jsonPath path to the json file to load the game from
	 * @return a Game object as described by the json file.
	 * @throws FileNotFoundException 
	 * @throws JSONException 
	 */
	public static Game createGame(String jsonPath) throws FileNotFoundException  {
		JSONObject json = new JSONObject(new JSONTokener(new FileReader(jsonPath)));
		return Game.createGame(json);
	}
	
	/**
	 * Create a game from a json object.
	 * @param json json object to load the game from
	 * @return a Game instance as described by the json object
	 */
	public static Game createGame(JSONObject json) {
		JSONObject jsonGoal = json.getJSONObject("goal-condition");
		
		Goal goal = Goal.createGoal(jsonGoal);
		Game game = new Game(goal);
		game.board = Board.createBoard(game, goal, json);
		
		return game;
	}
	
	/**
	 * Get a displayable string representation for this game's board. e.g.
	 * <br />
	 * <code>
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" + 
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;P&nbsp;W\n" +
	 *   <br />
	 *   &nbsp;&nbsp;"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
	 *   <br />
	 * </code>
	 * <br />
	 * is the representation for a 5 wide, 3 tall dungeon with a player at the
	 * centre and a wall two tiles to the right of the player.
	 * <br />
	 * <br />
	 * Note: information about portal and key pairings are lost when this
	 * function is called; the return value of this function may not be a valid
	 * argument to {@link #createMockGame(Goal, String...)};
	 * 
	 * @return a displayable string representation for this game's board
	 */
	public String getBoardString() {
		return this.board.getBoardString();
	}
	
	/**
	 * "Move" the player one tile in a particular direction.
	 * A movement consists of:
	 * <ol>
	 *   <li> Push in that direction. </li>
	 *   <li> Check if we can step in that direction (with no side-effects). </li>
	 *   <li> If we can step in that direction, do so.</li>
	 * </ol>
	 * @param d direction to move the player in.
	 */
	public void movePlayer(Direction d) {
		this.conurrentMovementLock.lock();
		try {
			this.player.push(d);
			if (this.player.canMove(d)) {
				this.player.move(d);
			}
		} finally {
			this.conurrentMovementLock.unlock();
		}
	}
	
	/**
	 * Signal that the player is attempting to swing their sword in a
	 * particular direction. They may or may not have a sword.
	 * @param d direction they are trying to swing their sword in
	 */
	public void swingSword(Direction d) {
		this.conurrentMovementLock.lock();
		try {
			this.player.swingSword(d);
		} finally {
			this.conurrentMovementLock.unlock();
		}
	}

	/**
	 * Signal that the player is attempting to swap a key in their possession
	 * with one on the ground that they're standing on. They may or may not
	 * be standing on a key, and they may or may not be holding a key.
	 */
	public void swapKey() {
		this.player.swapKey();
	}
	
	/**
	 * Permit all enemies to make a single valid movement (or stay still).
	 */
	public void moveEnemies() {
		for (Enemy enemy : new ArrayList<Enemy>(this.enemies)) {
			boolean isPlayerInvincible = player.isInvincible();
			
			Direction d = enemy.chooseMove(
				this.board.createWorldState(this.player.getLocation(), enemy.getLocation()),
				!isPlayerInvincible
			);

			if (d == null) {
				continue;
			}
			
			this.conurrentMovementLock.lock();
			try {
				if (player.isInvincible() && player.getLocation() == enemy.getLocation().adjacent(d)) {
					// Don't walk into the player.
				} else if (!enemy.hasDied() && enemy.canMove(d)) { 
					enemy.move(d);
				}
			} finally {
				this.conurrentMovementLock.unlock();
			}
		}
	}

	/**
	 * Track a player that is on the board. The tracked player will be the one
	 * which is moved by {@link #movePlayer(Direction)}.
	 * @param player player to track
	 */
	public void trackPlayer(Player player) {
		this.player = player;
		this.player.attachListener(this);
	}
	
	/**
	 * Track an enemy that is on this board. Tracked enemies will be moved by
	 * {@link #moveEnemies()}.
	 * @param enemy enemy to track
	 */
	public void trackEnemy(Enemy enemy) {
		this.enemies.add(enemy);
		// Listen for enemy death so that we know to stop trying to move them
		// once they die.
		enemy.attachListener(this);
	}

	/**
	 * Pass one second of duration for all potion effects. 
	 */
	public void tickBuffs() {
		this.player.tickBuffs();
	}
	
	/**
	 * Get whether the this Game's goal has been satisfied.
	 * @return true if the goal has been satisfied
	 */
	public boolean getHasWon() {
		return this.goal.isSatisfied();
	}
	
	/** 
	 * Get whether the game has been lost (the player has been killed).
	 * @return true if the game has been lost
	 */
	public boolean getHasLost() {
		return this.hasLost;
	}
	
	private void win() {
		this.hasWon = true;
		System.out.println("Game won.");
	}
	
	private void lose() {
		this.hasLost = true;
		System.out.println("Game lost.");
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof PlayerKilledEvent) {
			this.onPlayerKilled((PlayerKilledEvent) event);
		} else if (event instanceof EnemyKilledEvent) {
			this.onEnemyKilled((EnemyKilledEvent) event);
		}
	}
	
	private void onPlayerKilled(PlayerKilledEvent event) {
		this.lose();
	}
	
	private void onEnemyKilled(EnemyKilledEvent event) {
		Enemy who = event.getWhoDied();
		this.enemies.remove(who);
		who.detachListener(this);
	}

	public List<Cell> getCells() {
		return this.board.getCells();
	}

	public int getHeight() {
		return this.board.getHeight();
	}
	
	public int getWidth() {
		return this.board.getWidth();
	}
}
