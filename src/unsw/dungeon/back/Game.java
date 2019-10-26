package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface through which all changes to the Board state must occur.
 */
public class Game {
	private Board board;
	private Player player;
	private List<Enemy> enemies;
	
	private Game() {
	}
	
	/**
	 * Create a new Game object from a Board's string representation.
	 * @param boardString the string representation of the board
	 * @return a Game object
	 * @see {@link #getBoardString()}
	 */
	public static Game createGame(String boardString) {
		Game game = new Game();
		game.enemies = new ArrayList<Enemy>();
		game.board = Board.createBoard(boardString, game);
		return game;
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
	 */
	public String getBoardString() {
		return this.board.getBoardString();
	}
	
	/**
	 * Move the player one tile in a particular direction.
	 * @param d direction to move the player in.
	 */
	public void movePlayer(Direction d) {
		this.player.push(d);
		if (this.player.canMove(d)) {
			this.player.move(d);
		}
	}
	
	/**
	 * Permit all enemies to make a single valid movement (or stay still).
	 */
	public void moveEnemies() {
		for (Enemy enemy : this.enemies) {
			
			Direction d;
			double r = Math.random();
			if (r < 0.25) {
				d = Direction.UP;
			} else if (r < 0.5) {
				d = Direction.DOWN;
			} else if (r < 0.75) {
				d = Direction.LEFT;
			} else {
				d = Direction.RIGHT;
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
	}
	
	/**
	 * Track an enemy that is on this board. Tracked enemies will be moved by
	 * {@link #moveEnemies()}.
	 * @param enemy enemy to track
	 */
	public void trackEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}
	
}
