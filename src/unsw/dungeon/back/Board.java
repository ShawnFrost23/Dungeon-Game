package unsw.dungeon.back;

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
	
	private Board() {
		
	}
	
	/**
	 * Create a Board from a string representation. Valid string representations
	 * must have exactly one Player, see the docs for
	 * {@link Game#getBoardString()}.
	 * @param boardString a valid string representation of the Board
	 * @param game game which will be responsible for moving this Board's Player
	 * and Enemies.
	 * @return a Board object
	 */
	public static Board createBoard(String boardString, Game game) {
		Board board = new Board();
		String[] lines = boardString.split("\n");
		
		board.width = lines[0].length();
		board.height = lines.length;
		
		board.cells = new Cell[board.width][board.height];
		

		for (int lineNum = 0; lineNum < lines.length; ++lineNum) {
			String line = lines[lineNum];
			
			for (int charNum = 0; charNum < line.length(); ++charNum) {
				int x = charNum;
				int y = lineNum;
				Cell cell = new Cell(board, x, y);
				
				board.cells[x][y] = cell;
				
				char c = line.charAt(charNum);
				if (c == ' ') {
					
				} else if (c == 'W') {
					cell.addEntity(new Wall());
				} else if (c == 'P') {
					Player p = new Player();
					cell.addEntity(p);
					p.setLocation(cell);
					game.trackPlayer(p);
				} else if (c == 'B') {
					Boulder b = new Boulder();
					cell.addEntity(b);
					b.setLocation(cell);
				} else if (c == '!') {
					Enemy e = new Enemy();
					cell.addEntity(e);
					e.setLocation(cell);
					game.trackEnemy(e);
				}
			}
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
}
