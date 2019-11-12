package unsw.dungeon.back.event;

import unsw.dungeon.back.Cell;
import unsw.dungeon.back.Moveable;

/**
 * An event that is fired when a Cell requests that it be redrawn to the screen --
 * its texture information has changed in some way.
 */
public class CellRedrawEvent implements Event {
	private Cell whichCell;

	public CellRedrawEvent(Cell cell) {
		this.whichCell = cell;
	}

	public Cell getCell() {
		return this.whichCell;
	}
	

}
