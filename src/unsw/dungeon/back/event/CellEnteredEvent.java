package unsw.dungeon.back.event;

import unsw.dungeon.back.Moveable;

/**
 * An event that is fired whenever a Moveable enters a {@link Cell}.
 */
public class CellEnteredEvent implements Event {
	private Moveable whoEntered;
	
	public CellEnteredEvent(Moveable whoEntered) {
		this.whoEntered = whoEntered;
	}
	
	public Moveable getWhoEntered() {
		return this.whoEntered;
	}
}
