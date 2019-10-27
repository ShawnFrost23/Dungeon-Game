package unsw.dungeon.back.event;

import unsw.dungeon.back.Direction;
import unsw.dungeon.back.Player;

/**
 * An event that is fired whenever a Player attempts to push a {@link Cell} in a
 * particular direction.
 * Note: this event is fired in the cell that is being pushed, not in the cell
 * that the player is in when they're pushing.
 */
public class CellPushedEvent implements Event {
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
