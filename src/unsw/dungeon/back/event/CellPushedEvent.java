package unsw.dungeon.back.event;

import unsw.dungeon.back.Direction;
import unsw.dungeon.back.Player;

/**
 * An event that is fired whenever a Player attempts to push the contents of a
 * {@link unsw.dungeon.back.Cell Cell} in a particular direction.
 * Note: this event is fired in the cell that is being pushed, not in the cell
 * that the player is in when they're pushing.
 */
public class CellPushedEvent implements Event {
	private Player whoPushed;
	private Direction directionPushed;
	
	/**
	 * Construct a new CellPushedEvent.
	 * @param whoPushed the player who instigated this push event
	 * @param directionPushed the direction the instigator is pushing in
	 */
	public CellPushedEvent(Player whoPushed, Direction directionPushed) {
		this.whoPushed = whoPushed;
		this.directionPushed = directionPushed;
	}
	
	/**
	 * Get the player who instigated this push event.
	 * @return the player who instigated this push event
	 */
	public Player getWhoPushed() {
		return this.whoPushed;
	}

	/**
	 * Get the direction the instigator of this event is pushing in. 
	 * @return direction the push is in
	 */
	public Direction getDirectionPushed() {
		return this.directionPushed;
	}
}
