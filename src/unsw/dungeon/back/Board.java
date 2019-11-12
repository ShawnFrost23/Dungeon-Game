package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
	 * Create a new {@link Board} from a json form
	 * @param game Game object to which this board will belong
	 * @param goal the Game's goal
	 * @param json the json object representing the board
	 * @return a new {@link Board instance}
	 */
	public static Board createBoard(Game game, Goal goal, JSONObject json) {
		int width = json.getInt("width");
		int height = json.getInt("height");
		
		Board board = new Board(width, height);
		JSONArray jsonEntities = json.getJSONArray("entities");
		board.addEntities(jsonEntities, game, goal);
		

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

	// TODO: If we can convert boardString[] into json, then we can remove the
	// duplication here just by creating an adaptor to its namesake
	// addEntities(JSONArray jsonEntities)
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

	private void addEntities(JSONArray jsonEntities, Game game, Goal goal) {
		for (int i = 0; i < jsonEntities.length(); ++i) {
			JSONObject jsonEntity = jsonEntities.getJSONObject(i);
			String type = jsonEntity.getString("type");
			int x = jsonEntity.getInt("x");
			int y = jsonEntity.getInt("y");
			Cell cell = this.cells[x][y];
			Entity e = null;
			switch (type) {
			case "wall":
				e = new Wall();
				break;
			case "player":
				e = new Player(cell);
				game.trackPlayer((Player) e);
				break;
			case "boulder":
				e = new Boulder(cell);
				break;
			case "enemy":
				e = new Enemy(cell, new NaiveMovementStrategy());
				game.trackEnemy((Enemy) e);
				break;
			case "switch":
				e = new FloorSwitch();
				break;
			case "portal":
				throw new Error("Portal loader not implemented!");
//				e = new Portal (cell);
//				if (firstPortal == null) {
//					firstPortal = ((Portal) e);
//				} else {
//					// If we've already added a portal this round, then pair
//					// it with the one we're adding just now.
//					((Portal) e).setPairedPortal(firstPortal);
//					firstPortal.setPairedPortal((Portal) e);
//				}
//				break;
			case "door":
				throw new Error("Door loader not implemented!");
//				e = new Door(boardNum);
//				break;
			case "key":
				throw new Error("Key loader not implemented!");
//				e = new Key(boardNum);
//				break;
			case "treasure":
				e = new Treasure(cell);
				break;
			case "sword":
				e = new Sword(cell);
				break;
			case "exit":
				e = new Exit(); 
				break;
			case "invincibility":
				e = new InvincibilityPotion();
				break;
			default:
				throw new Error("Unrecognised entity type \"" + type + "\".");
			}
			
			// make two passes -- one for non-moveables, one for moveables.
			if (e instanceof Moveable) {
				cell.enter((Moveable) e);
			} else {
				cell.addEntity(e);
			}
			goal.trackEntity(e);
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

	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
}