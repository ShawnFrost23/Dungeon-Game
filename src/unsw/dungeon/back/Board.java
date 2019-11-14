package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		JSONObject json = Board.boardStringsToJSON(boardStrings);
		return Board.createBoard(game, goal, json);
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
	 * Get a displayable string representation of this Board. In each cell, only
	 * the entity with the largest {@link Entity#getZ()} will be displayed.
	 * @return string representation of this Board
	 */
	public String getBoardString() {
		String s = "";
		for (int j = 0; j < this.height; ++j) {
			for (int i = 0; i < this.width; ++i) {
				List<Texture> textures = cells[i][j].getTextures();
				s += textures.get(textures.size() - 1).getChar();
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

	private void addEntities(JSONArray jsonEntities, Game game, Goal goal) {
		Map<Integer, Portal> portals = new HashMap<Integer, Portal>();
		
		// Sort entities so that moveables will be placed after immovables.
		// This will mean that e.g. Boulders that are loaded on top of floor
		// switches will correctly trigger them.
		List<JSONObject> jsonEntityList = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonEntities.length(); ++i) {
			JSONObject jsonEntity = jsonEntities.getJSONObject(i);
			String type = jsonEntity.getString("type");
			if (type.equals("player") || type.equals("boulder") || type.equals("enemy")) {
				jsonEntityList.add(jsonEntity);
			} else {
				jsonEntityList.add(0, jsonEntity);
			}
			
		}
		for (JSONObject jsonEntity : jsonEntityList) {
			String type = jsonEntity.getString("type");
			int x = jsonEntity.getInt("x");
			int y = jsonEntity.getInt("y");
			Cell cell = this.cells[x][y];
			Entity e = null;
			int id;
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
				e = new Enemy(cell, new IntelligentMovementStrategy());
				game.trackEnemy((Enemy) e);
				break;
			case "switch":
				e = new FloorSwitch();
				break;
			case "portal":
				id = jsonEntity.getInt("id");
				e = new Portal (cell);
				if (portals.get(id) == null) {
					portals.put(id, (Portal) e);
				} else {
					((Portal) e).setPairedPortal(portals.get(id));
					portals.get(id).setPairedPortal((Portal) e);
				}
				break;
			case "door":
				id = jsonEntity.getInt("id");
				e = new Door(id);
				break;
			case "key":
				id = jsonEntity.getInt("id");
				e = new Key(id);
				break;
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
			case "spoof-crushable-item":
				e = new SpoofCrushableItem(cell);
				break;
			default:
				throw new Error("Unrecognised entity type \"" + type + "\".");
			}
			if (e instanceof Moveable) {
				cell.enter((Moveable) e);
			} else {
				cell.addEntity(e);
			}
			goal.trackEntity(e);
		}
	}
	
	public static JSONObject boardStringsToJSON(String ...boardStrings) {
		JSONObject json = new JSONObject();

		String[] lines = boardStrings[0].split("\n");
		int width = lines[0].length();
		int height = lines.length;

		json.put("width", width);
		json.put("height", height);
		
		JSONArray jsonEntities = new JSONArray();
		
		int id = 0;
		
		for (String boardString : boardStrings) {
			lines = boardString.split("\n");
			for (int y = 0; y < height; ++y) {
				String line = lines[y];
				for (int x = 0; x < width; ++x) {
					char c = line.charAt(x);
					if (c == ' ') {
						continue;
					}

					JSONObject jsonEntity = new JSONObject();
					jsonEntity.put("x", x);
					jsonEntity.put("y", y);
					
					String type;
					if (c == 'W') {
						type = "wall";
					} else if (c == 'P') {
						type = "player";
					} else if (c == 'B') {
						type = "boulder";
					} else if (c == '!') {
						type = "enemy";
					} else if (c == '_') {
						type = "switch";
					} else if (c == 'O') {
						type = "portal";
						jsonEntity.put("id", id);
					} else if (c == '#') {
						type = "door";
						jsonEntity.put("id", id);
					} else if (c == '~') {
						type = "key";
						jsonEntity.put("id", id);
					} else if (c == 'T') {
						type = "treasure";
					} else if (c == 'S') {
						type = "sword";
					} else if (c == 'E') {
						type = "exit";
					} else if (c == '*') {
						type = "invincibility";
					} else if (c == '?') {
						type = "spoof-crushable-item";
					} else {
						throw new Error("Unrecognised entity texture \"" + c + "\".");
					}
					jsonEntity.put("type", type);

					jsonEntities.put(jsonEntity);
				}
			}
			id += 1;
		}
		json.put("entities", jsonEntities);
		
		return json;
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