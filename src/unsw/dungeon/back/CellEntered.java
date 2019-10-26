package unsw.dungeon.back;

public class CellEntered implements CellEvent {
	private Moveable whoEntered;
	
	public CellEntered(Moveable whoEntered) {
		this.whoEntered = whoEntered;
	}
	
	public Moveable getWhoEntered() {
		return this.whoEntered;
	}
}
