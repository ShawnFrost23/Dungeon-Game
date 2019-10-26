package unsw.dungeon.back;

public class Board {
	private Cell[][] cells;
	private int height;
	private int width;
	
	private Board() {
		
	}
	
	public static Board createBoard(String boardString, Game game) {
		Board board = new Board();
		String[] lines = boardString.split("\n");
		
		board.width = lines[1].length();
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
					game.setPlayer(p);
				} else if (c == 'B') {
					Boulder b = new Boulder();
					cell.addEntity(b);
					b.setLocation(cell);
				} else if (c == '!') {
					Enemy e = new Enemy();
					cell.addEntity(e);
					e.setLocation(cell);
					game.addEnemy(e);
				}
			}
			
		}
		
		return board;
	}
	
	
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
