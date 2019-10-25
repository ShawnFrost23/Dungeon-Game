package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private Board board;
	private int x;
	private int y;
	
	private List<Entity> entities;
	
	public Cell(Board board, int x, int y) {
		this.x = x;
		this.y = y;
		this.board = board;
		this.entities = new ArrayList<Entity>();
	}

	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public void addEntity(Entity e) {
		this.entities.add(e);
	}
	public void removeEntity(Entity e) {
		this.entities.remove(e);
	}

	public char getTexture() {
		if (this.entities.size() == 0) {
			return ' ';
		}
		
		int maxZ = this.entities.get(0).getZ();
		char texture = this.entities.get(0).getTexture();
		for (Entity entity : this.entities) {
			if (entity.getZ() > maxZ) {
				maxZ = entity.getZ(); 
				texture = entity.getTexture();
			}
		}
		
		return texture;
	}
	
	public Cell adjacent(Direction d) {
		return board.adjacent(this, d);
	}
	
	public boolean willPreventEntry(MoveableEntity m, Direction d) {
		for (Entity entity : this.entities) {
			if (entity.willPreventEntry(m, d)) {
				return true;
			}
		}
		return false;
	}
	
	public void exit(MoveableEntity m, Direction d) {
		this.entities.remove(m);
		for (Entity entity : new ArrayList<Entity>(this.entities)) {
			entity.onExit(m, d);
		}
	}
	
	public void enter(MoveableEntity m, Direction d) {
		for (Entity entity : new ArrayList<Entity>(this.entities)) {
			entity.onEnter(m, d);
		}
		this.entities.add(m);
	}
}
