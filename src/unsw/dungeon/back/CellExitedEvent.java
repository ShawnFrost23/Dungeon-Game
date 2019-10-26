package unsw.dungeon.back;

public class CellExitedEvent implements CellEvent {
	private Moveable whoExited;
	
	public CellExitedEvent(Moveable whoExited) {
		this.whoExited = whoExited;
	}
	
	public Moveable getWhoExited() {
		return this.whoExited;
	}
}