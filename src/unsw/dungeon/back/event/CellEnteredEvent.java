package unsw.dungeon.back.event;

import unsw.dungeon.back.Moveable;

/**
 * An event that is fired whenever a Moveable enters a
 * {@link unsw.dungeon.back.Cell Cell}.
 */
public class CellEnteredEvent implements Event {
	private Moveable whoEntered;
	
	/**
	 * Construct a new CellEnteredEvent.
	 * @param whoEntered Moveable who entered the cell
	 */
	public CellEnteredEvent(Moveable whoEntered) {
		this.whoEntered = whoEntered;
	}
	
	/**
	 * Get the Moveable who triggered this event.
	 * @return Moveable who triggered this event
	 */
	public Moveable getWhoEntered() {
		return this.whoEntered;
	}
}
