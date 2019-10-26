package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

// TODO: enforce "Cell's shouldn't expose any notion of coordinates".
// TODO: add push(Dir) method here.
// TODO: ... a lot of redesign here.

/**
 * A collection of Entities at one discrete location.
 */
public class Cell {
	private Board board;
	private int x;
	private int y;
	
	private List<Entity> entities;
	
	/**
	 * Construct a new Cell.
	 * @param board board to which this cell belongs
	 * @param x x-coordinate of the cell
	 * @param y y-cooridnate of the cell
	 */
	public Cell(Board board, int x, int y) {
		this.x = x;
		this.y = y;
		this.board = board;
		this.entities = new ArrayList<Entity>();
	}

	/**
	 * Get this Cell's x-coordinate. This function should only be used by Board;
	 * Cell's shouldn't expose any notion of coordinates, just know what is
	 * directly adjacent to them.
	 * @return this Cell's x-coordinate
	 */
	public int getX() {
		return this.x;
	}
	/**
	 * Get this Cell's y-coordinate. This function should only be used by Board;
	 * Cell's shouldn't expose any notion of coordinates, just know what is
	 * directly adjacent to them.
	 * @return this Cell's y-coordinate
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Add an entity to this cell. Should only be used to create a Board.
	 * @param e entity to add
	 */
	public void addEntity(Entity e) {
		this.entities.add(e);
	}

	/**
	 * Get the texture for this cell. This will be the texture of the entity on
	 * this cell with the largest z-index, or ' ' if none are present.
	 * @return this cell's texture
	 * @see {@link Entity#getTexture()}
	 * @see {@link Entity#getZ()}
	 */
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
	
	/**
	 * Get the cell that is adjacent to this one.
	 * @param d direction to get adjacent in
	 * @return the cell that is adjacent to this one in direction <b>d</b>
	 */
	public Cell adjacent(Direction d) {
		return board.adjacent(this, d);
	}
	
	/**
	 * True if this Cell will prevent an entry attempt by <b>m</b> -- <b>m</b>
	 * cannot walk atop of it.s
	 * @param m MoveableEntity to whether or not en
	 * @param d
	 * @return
	 */
	public boolean willPreventEntry(MoveableEntity m, Direction d) {
		for (Entity entity : this.entities) {
			if (entity.willPreventEntry(m, d)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Notify all entities on this Cell that MoveableEntity <b>m</b> has just
	 * left. Furthermore, declare to this Cell itself that <b>m</b> has left.
	 * @param m MoveableEntity that left this Cell
	 * @param d Direction they left in
	 */
	public void exit(MoveableEntity m, Direction d) {
		this.entities.remove(m);
		for (Entity entity : new ArrayList<Entity>(this.entities)) {
			entity.onExit(m, d);
		}
	}

	/**
	 * Notify all entities on this Cell that MoveableEntity <b>m</b> has just
	 * entered. Furthermore, declare to this Cell itself that <b>m</b> has
	 * entered.
	 * @param m MoveableEntity that entered this Cell
	 * @param d Direction they came from
	 */
	public void enter(MoveableEntity m, Direction d) {
		for (Entity entity : new ArrayList<Entity>(this.entities)) {
			entity.onEnter(m, d);
		}
		this.entities.add(m);
	}
}
