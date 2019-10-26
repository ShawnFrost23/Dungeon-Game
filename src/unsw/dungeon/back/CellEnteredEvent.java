package unsw.dungeon.back;

/**
 * An event that is fired whenever a Moveable enters a cell.
 */
public class CellEnteredEvent implements CellEvent {
	private Moveable whoEntered;
	
	public CellEnteredEvent(Moveable whoEntered) {
		this.whoEntered = whoEntered;
	}
	
	public Moveable getWhoEntered() {
		return this.whoEntered;
	}
}
