package unsw.dungeon.back.event;

import unsw.dungeon.back.Cell;
import unsw.dungeon.back.Direction;

/**
 * An event that is fired whenever a Player swings a sword in a particular
 * {@link unsw.dungeon.back.Cell Cell}.
 * Note: this event is fired in the cell that is being hit with the sword, not
 * the cell the player was in when they swung the sword.
 */
public class CellHitWithSwordEvent implements Event {
	private Cell whichCell;
	private Direction direction;

	public CellHitWithSwordEvent(Cell cell, Direction direction) {
		this.whichCell = cell;
		this.direction = direction;
	}
	
	public Cell getCell() {
		return this.whichCell;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
}