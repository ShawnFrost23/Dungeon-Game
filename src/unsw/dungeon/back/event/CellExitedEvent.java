package unsw.dungeon.back.event;

import unsw.dungeon.back.Moveable;

/**
 * An event that is fired whenever a Moveable exits a
 * {@link unsw.dungeon.back.Cell Cell}.
 */
public class CellExitedEvent implements Event {
	private Moveable whoExited;
	
	/**
	 * Construct a new CellExitedEvent.
	 * @param whoExited Moveable who exited the cell
	 */
	public CellExitedEvent(Moveable whoExited) {
		this.whoExited = whoExited;
	}
	
	/**
	 * Get the Moveable who triggered this event.
	 * @return Moveable who triggered this event
	 */
	public Moveable getWhoExited() {
		return this.whoExited;
	}
}