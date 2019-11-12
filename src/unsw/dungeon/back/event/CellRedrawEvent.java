package unsw.dungeon.back.event;

import unsw.dungeon.back.Cell;
import unsw.dungeon.back.Moveable;

public class CellRedrawEvent implements Event {
	private Cell whichCell;

	public CellRedrawEvent(Cell cell) {
		this.whichCell = cell;
	}

	public Cell getCell() {
		return this.whichCell;
	}
	

}
