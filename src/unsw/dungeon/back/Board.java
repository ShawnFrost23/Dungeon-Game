package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.spoof.SpoofCrushableItem;

/**
 * A collection of cells. Board can tell its user what's to the left, right,
 * above, or below each (non-edge) cell.
 */
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

	/**
	 * Create a new  {@link Board} from boardString form.
	 * @param game Game object to which this board will belong
	 * @param goal the Game's goal
	 * @param boardStrings a valid string representation of the board, see 
	 * {@link Game#createGame}
	 * @return a new {@link Board} instance
	 */
	public static Board createBoard(Game game, Goal goal, String ...boardStrings) {
		int subBoardNum = 0;
		Board board = null;
		for (String boardString : boardStrings) {
			if (board == null) {
				String[] lines = boardString.split("\n");
				board = new Board(lines[0].length(), lines.length);
			}

			board.addEntities(boardString, game, goal, subBoardNum);
			subBoardNum += 1;
		}

		return board;
	}

	/**
	 * Get the Cell that is adjacent in coordinates to the provided cell.
	 * @param c cell to check next to
	 * @param d direction to check in
	 * @return cell that is adjacent to the given cell in the given direction
	 */
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
	 * Get a displayable string representation of this Board.
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

	/**
	 * Create a {@link WorldState} object corresponding to this Board. 
	 * @param playerLocation cell the player is located in
	 * @param enemyLocation cell the enemy who's using this function to make
	 * plans is located in
	 * @return a new {@link WorldState} object corresponding to this Board 
	 */
	public WorldState createWorldState(Cell playerLocation, Cell enemyLocation) {
		return new WorldState(this.cells, this.height, this.width, enemyLocation, playerLocation);
	}

	private void addEntities(String boardString, Game game, Goal goal, int boardNum) {
		String[] lines = boardString.split("\n");

		Portal firstPortal = null;
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
				} else if (c == 'O') {
					e = new Portal (cell);
					if (firstPortal == null) {
						firstPortal = ((Portal) e);
					} else {
						// If we've already added a portal this round, then pair
						// it with the one we're adding just now.
						((Portal) e).setPairedPortal(firstPortal);
						firstPortal.setPairedPortal((Portal) e);
					}
				} else if (c == '#') {
					e = new Door(boardNum);
				} else if (c == '~') {
					e = new Key(boardNum);
				} else if (c == 'T' ) {
					e = new Treasure(cell);
				} else if (c == 'S' ) {
					e = new Sword(cell);
				} else if (c == 'E') {
					e = new Exit(); 
				} else if (c == '*') {
					e = new InvincibilityPotion();
				} else if (c == '?') {
					e = new SpoofCrushableItem(cell);
				} else {
					throw new Error("Unrecognised entity texture '" + c + "'");
				}

				if (e != null) {
					if (e instanceof Moveable) {
						cell.enter((Moveable) e);
					} else {
						cell.addEntity(e);
					}
					goal.trackEntity(e);
				}
			}
		}
	}

	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		for (int y = 0; y < this.height; ++y) {
			for (int x = 0; x < this.width; ++x) {
				cells.add(this.cells[x][y]);
			}
		}
		return cells;
	}

}