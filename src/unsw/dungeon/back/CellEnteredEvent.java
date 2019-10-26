package unsw.dungeon.back;

public class CellEnteredEvent implements CellEvent {
	private Moveable whoEntered;
	
	public CellEnteredEvent(Moveable whoEntered) {
		this.whoEntered = whoEntered;
	}
	
	public Moveable getWhoEntered() {
		return this.whoEntered;
	}
}
