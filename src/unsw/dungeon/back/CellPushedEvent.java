package unsw.dungeon.back;

public class CellPushedEvent implements CellEvent {
	private Player whoPushed;
	private Direction directionPushed;
	
	public CellPushedEvent(Player whoPushed, Direction directionPushed) {
		this.whoPushed = whoPushed;
		this.directionPushed = directionPushed;
	}
	
	public Player getWhoPushed() {
		return this.whoPushed;
	}

	public Direction getDirectionPushed() {
		return this.directionPushed;
	}
	
}
