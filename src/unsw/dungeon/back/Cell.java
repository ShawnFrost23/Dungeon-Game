package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Subject;
import unsw.dungeon.back.event.CellExitedEvent;
import unsw.dungeon.back.event.CellPushedEvent;

// TODO: enforce "Cells shouldn't expose any notion of coordinates".

/**
 * A collection of Entities at one discrete location.
 */
public class Cell implements Subject {
	private Board board;
	private int x;
	private int y;
	
	private List<Entity> entities;
	private List<Observer> observers;
	
	/**
	 * Construct a new Cell.
	 * @param board board to which this cell belongs
	 * @param x x-coordinate of the cell
	 * @param y y-coordinate of the cell
	 */
	public Cell(Board board, int x, int y) {
		this.x = x;
		this.y = y;
		this.board = board;
		this.entities = new ArrayList<Entity>();
		this.observers = new ArrayList<Observer>();
	}

	/**
	 * Get this Cell's x-coordinate. This function should only be used by Board;
	 * Cells shouldn't expose any notion of coordinates, just know what is
	 * directly adjacent to them.
	 * @return this Cell's x-coordinate
	 */
	public int getX() {
		return this.x;
	}
	/**
	 * Get this Cell's y-coordinate. This function should only be used by Board;
	 * Cells shouldn't expose any notion of coordinates, just know what is
	 * directly adjacent to them.
	 * @return this Cell's y-coordinate
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Add an entity to this cell.
	 * @param e entity to add
	 */
	public void addEntity(Entity e) {
		this.entities.add(e);
		if (e instanceof Observer) {
			this.observers.add((Observer) e);
		}
	}

	/**
	 * Remove an entity from this cell.
	 * @param e entity to remove
	 */
	public void removeEntity(Entity e) {
		this.entities.remove(e);
		if (e instanceof Observer) {
			this.observers.remove((Observer) e);
		}
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
	 * Determine whether this cell should prevent
	 * {@link MoveableEntity MoveableEntities} from entering it.
	 * @return true if any entities on this cell are collidable
	 */
	public boolean isCollidable() {
		for (Entity e : this.entities) {
			if (e instanceof Collidable) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Declare that Moveable m has exited this Cell. Notify all listeners.
	 * @param m MoveableEntity that left this Cell
	 */
	public void exit(Moveable m) {
		this.removeEntity(m);
		this.notifyAllOf(new CellExitedEvent(m));
	}

	/**
	 * Declare that Moveable m has entered this Cell. Notify all listeners.
	 * @param m MoveableEntity that entered this Cell
	 */
	public void enter(Moveable m) {
		this.notifyAllOf(new CellEnteredEvent(m));
		this.addEntity(m);
	}

	/**
	 * Declare that a Player has attempted to push the things in this cell.
	 * Notify all listeners.
	 * @param p Player who's pushing
	 * @param d direction they're pushing
	 */
	public void push(Player p, Direction d) {
		this.notifyAllOf(new CellPushedEvent(p, d));
	}
	
	@Override
	public void attachListener(Observer observer) {
		this.observers.add(observer);
		
	}

	@Override
	public void detachListener(Observer observer) {
		this.observers.remove(observer);
	}

	/**
	 * Notify all observers that a CellEvent has been fired.
	 * @param event event that has been fired
	 */
	@Override
	public void notifyAllOf(Event event) {
		for (Observer observer : new ArrayList<Observer>(this.observers)) {
			observer.notifyOf(event);
			
		}
	}
	
}
