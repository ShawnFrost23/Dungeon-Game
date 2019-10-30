package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.spoof.*;

// TODO: Board creational methods ...
// TODO: my source of unease ... `Cell location` -- a cell isn't a location!

/**
 * A collection of cells. Board can tell its user what's effectively to the
 * left, right, above, or below each (non-edge) cell.
 * For the most part, a Board will act like a 2d array of cells.
 */
// "For the most part", "effectively" = portals are static things that affect
// space itself, so it might make sense to have them interact directly with the
// board class rather than being an entity. I think this will simplify portals
// EXTREMELY considering the way we are currently going about things.
//
// But, this will mean that just by checking adjacent, enemies will not be able
// to know whether they're walking into a portal or not. We'll need to add some
// additional interface functions here, or give Enemies something other than
// a Board to plan their moves on. Both seem sane.
public class Board {
	private Cell[][] cells;
	private int height;
	private int width;
	
	private Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.cells = new Cell[width][height];
		
		for (int y = 0; y < this.height; ++y) {
			for (int x = 0; x < this.width; ++x) {
				this.cells[x][y] = new Cell(this, x, y);
			}
		}
	}
	
	public static Board createBoard(Game game, Goal goal, String ...boardStrings) {
		Board board = null;
		for (String boardString : boardStrings) {
			if (board == null) {
				String[] lines = boardString.split("\n");
				board = new Board(lines[0].length(), lines.length);
			}
			
			board.addEntities(boardString, game, goal);
		}
		
		return board;
	}
	
	/**
	 * Get the Cell that is effectively adjacent to <b>c</b> in direction
	 * <b>d</b>.
	 * @param c cell to check next to
	 * @param d direction to check in 
	 * @return cell that is adjacent to the given cell in the given direction
	 */
	// Note: this doc will have to be updated when Portals are implemented.
	public Cell adjacent(Cell c, Direction d) { 
		Cell adj = null;
		if (d == Direction.UP) {
			adj = this.cells[c.getX()][c.getY() - 1];
		} else if (d == Direction.DOWN) {
			adj = this.cells[c.getX()][c.getY() + 1];
		} else if (d == Direction.LEFT) {
			adj = this.cells[c.getX() - 1][c.getY()];
		} else if (d == Direction.RIGHT) {
			adj = this.cells[c.getX() + 1][c.getY()];
		}
		return adj;
	}
	
	/**
	 * Get a string representation of this Board.
	 * @return string representation of this Board
	 */
	public String getBoardString() {
		String s = "";
		for (int j = 0; j < this.height; ++j) {
			for (int i = 0; i < this.width; ++i) {
				s += cells[i][j].getTexture();
			}
			s += "\n";
		}
		return s;
	}
	
	public WorldState createWorldState(Cell playerLocation, Cell enemyLocation) {
		return new WorldState(this.cells, this.height, this.width, enemyLocation, playerLocation);
	}

	private void addEntities(String boardString, Game game, Goal goal) {
		String[] lines = boardString.split("\n");

		for (int y = 0; y < this.height; ++y) {
			String line = lines[y];
			for (int x = 0; x < this.width; ++x) {
				Cell cell = this.cells[x][y];
				
				char c = line.charAt(x);
				Entity e = null;
				
				if (c == ' ') {
					
				} else if (c == 'W') {
					e = new Wall();
				} else if (c == 'P') {
					e = new Player(cell);
					game.trackPlayer((Player) e);
				} else if (c == 'B') {
					e = new Boulder(cell);
				} else if (c == '!') {
					e = new Enemy(cell, new NaiveMovementStrategy());
					game.trackEnemy((Enemy) e);
				} else if (c == '_') {
					e = new FloorSwitch();
				} else if (c == 'T' ) {
					e = new Treasure(cell);
				} else if (c == 'E') {
					e = new Exit(cell); 
				}
				
				// If the item is a "spoof item" for testing, load it in.
				if (c == '?') {
					e = new SpoofCrushableItem(cell);
				}
				
				if (e != null) {
					if (e instanceof Moveable) {
						// Trigger events when we add!
						cell.enter((Moveable) e);
					} else {
						cell.addEntity(e);
					}
					goal.trackEntity(e);
				}
			}
		}
	}
}
