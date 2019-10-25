package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private Board board;
	private Player player;
	private List<Enemy> enemies;
	
	private Game() {
		//this.b = new Board(30, 20);
		//b.cellAt(3, 3).insert(new Player(b.cellAt(3, 3)));
	}
	
	
	public static Game createGame(String boardString) {
		Game game = new Game();
		game.enemies = new ArrayList<Enemy>();
		game.board = Board.createBoard(boardString, game);
		return game;
	}
	
	public void display() {
		this.board.display();
	}
	
	public void movePlayer(Direction d) {
		if (this.player.canMove(d)) {
			this.player.move(d);
		}
	}
	
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


	public void setPlayer(Player player) {
		this.player = player;
	}
	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}
	
}
