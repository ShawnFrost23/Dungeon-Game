package unsw.dungeon.back.event;

import unsw.dungeon.back.Moveable;

/**
 * An event that is fired whenever a Moveable exits a cell.
 */
public class CellExitedEvent implements CellEvent {
	private Moveable whoExited;
	
	public CellExitedEvent(Moveable whoExited) {
		this.whoExited = whoExited;
	}
	
	public Moveable getWhoExited() {
		return this.whoExited;
	}
}