package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.EnemyKilledEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PlayerKilledEvent;

/**
 * The interface through which all changes to the Board state must occur.
 */
public class Game implements Observer {
	private Board board;
	private Player player;
	private List<Enemy> enemies;
	private Goal goal;

	private boolean hasWon;
	private boolean hasLost;
	
	private Game(Goal goal) {
		this.hasWon = false;
		this.hasLost = false;
		this.enemies = new ArrayList<Enemy>();
		this.goal = goal;
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
	 * Note: Proceeding from the first string in <b>boardStrings</b> last, when
	 * {@link Moveable} entities are spawned, only entities that have already 
	 * been loaded will be sent
	 * {@link unsw.dungeon.back.event.CellEnteredEvent CellEnteredEvents}. 
	 * <br />
	 * @param goal Goal object representing the Game's win conditions
	 * @param boardStrings valid string representation of Entities on the board
	 * @return a Game object with entities as specified in <b>boardStrings</b>
	 * @see {@link #getBoardString()}
	 */
	public static Game createGame(Goal goal, String ...boardStrings) {
		Game game = new Game(goal);
		game.board = Board.createBoard(game, goal, boardStrings);
		return game;
	}
	
	/**
	 * Declare that the game has been won; print "Game won." to the screen.
	 */
	private void win() {
		this.hasWon = true;
		System.out.println("Game won.");
	}
	
	/**
	 * Declare that the game has been lost; print "Game lost." to the screen.
	 */
	private void lose() {
		this.hasLost = true;
		System.out.println("Game lost.");
	}
	
	/**
	 * Get a string representation for this game's board. e.g.
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
	 * 
	 * @return a string representation for this game's board.
	 * @see {@link #createGame(String...)}
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
		this.player.push(d);
		if (this.player.canMove(d)) {
			this.player.move(d);
		}
	}
	
	public void swingSword(Direction d) {
		this.player.swingSword(d);
	}
	
	/**
	 * Permit all enemies to make a single valid movement (or stay still).
	 */
	public void moveEnemies() {
		for (Enemy enemy : this.enemies) {
			Direction d = enemy.chooseMove(this.board.createWorldState(this.player.getLocation(), enemy.getLocation()));
			if (d == null) {
				continue;
			}
			if (enemy.canMove(d)) {
				enemy.move(d);
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

	public boolean getHasWon() {
		return this.goal.isSatisfied();
	}
	
	public boolean getHasLost() {
		return this.hasLost;
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
}
