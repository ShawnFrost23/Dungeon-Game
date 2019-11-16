package unsw.dungeon.back.event;

import unsw.dungeon.back.Cell;
import unsw.dungeon.back.Direction;

/**
 * An event that is fired by a Cell whenever a key is dropped into it (as is the
 * case when a player swaps a key)
 * {@link unsw.dungeon.back.Cell Cell}.
 */
public class CellKeyDroppedEvent implements Event {
	private Cell whichCell;
	public CellKeyDroppedEvent(Cell cell) {
		this.whichCell = cell;
	}
	
	public Cell getCell() {
		return this.whichCell;
	}
}
