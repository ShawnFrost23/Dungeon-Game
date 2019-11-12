package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Subject;
import unsw.dungeon.back.event.CellExitedEvent;
import unsw.dungeon.back.event.CellHitWithSwordEvent;
import unsw.dungeon.back.event.CellPushedEvent;
import unsw.dungeon.back.event.CellRedrawEvent;

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
	 * Get this Cell's x-coordinate. Coordinates should only be used in the
	 * context of a board.
	 * @return this Cell's x-coordinate
	 */
	public int getX() {
		return this.x;
	}
	/**
	 * Get this Cell's y-coordinate. Coordinates should only be used in the
	 * context of a board.
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
			this.attachListener((Observer) e);
		}
		
		this.notifyAllOf(new CellRedrawEvent(this));
	}

	/**
	 * Remove an entity from this cell.
	 * @param e entity to remove
	 */
	public void removeEntity(Entity e) {
		this.entities.remove(e);
		if (e instanceof Observer) {
			this.detachListener((Observer) e);
		}
		
		this.notifyAllOf(new CellRedrawEvent(this));
	}
	
	/**
	 * Get the all of the entity textures in this cell, sorted in increasing
	 * z. A dirt texture will be prefixed to the list.
	 * @return list of the textures in this cell
	 * @see {@link Entity#getTexture()}
	 * @see {@link Entity#getZ()}	
	 */
	public List<Texture> getTextures() {
		List<Texture> textures = new ArrayList<Texture>();
		textures.add(new Texture(' ', "dirt_0_new.png"));
		List<Entity> zSortedEntities = new ArrayList<Entity>(this.entities);
		zSortedEntities.sort(
			(Entity a, Entity b) -> a.getZ() < b.getZ() ? -1 : 1
		);
		
		for (Entity e : zSortedEntities) {
			textures.add(e.getTexture());
		}
		return textures;
	}
	
	/**
	 * Get the cell that is adjacent to this one.
	 * @param d direction to get adjacent in
	 * @return the cell that is adjacent to this one in direction <b>d</b>
	 */
	public Cell adjacent(Direction d) {
		Cell adjacent = board.adjacent(this, d);

		for (Entity entity : adjacent.entities) {
			if (entity instanceof Portal) {
				Portal portal = (Portal) entity;
				return portal.getPairedAdjacent(d);
			}
		}
		return adjacent;
	}
	
	/**
	 * Determine whether this cell should prevent
	 * {@link MoveableEntity MoveableEntities} from entering it.
	 * @return true if any entities on this cell are collidable
	 */
	public boolean isCollidable() {
		for (Entity e : this.entities) {
			if (e.isCollidable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Declare that Moveable m has exited this Cell. Generate a
	 * {@link unsw.dungeon.back.event.CellExitedEvent CellExitedEvent} 
	 * and notify all listeners.
	 * @param m MoveableEntity that left this Cell
	 */
	public void exit(Moveable m) {
		this.removeEntity(m);
		this.notifyAllOf(new CellExitedEvent(m));
	}

	/**
	 * Declare that Moveable m has entered this Cell. Generate a
	 * {@link unsw.dungeon.back.event.CellEnteredEvent CellEnteredEvent} 
	 * and notify all listeners.
	 * @param m MoveableEntity that entered this Cell
	 */
	public void enter(Moveable m) {
		this.notifyAllOf(new CellEnteredEvent(m));
		this.addEntity(m);
	}

	/**
	 * Declare that a Player has attempted to push the things in this cell.
	 * Generate a
	 * {@link unsw.dungeon.back.event.CellPushedEvent CellPushedEvent}
	 * and notify all listeners.
	 * @param p Player who's pushing
	 * @param d direction they're pushing
	 */
	public void push(Player p, Direction d) {
		this.notifyAllOf(new CellPushedEvent(p, d));
	}

	/**
	 * Declare that a Player has swung their sword in this cell.
	 * @param d
	 */
	public void hitWithSword() {
		this.notifyAllOf(new CellHitWithSwordEvent());	
	}
	
	@Override
	public void attachListener(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void detachListener(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyAllOf(Event event) {
		for (Observer observer : new ArrayList<Observer>(this.observers)) {
			observer.notifyOf(event);
		}
	}
}
