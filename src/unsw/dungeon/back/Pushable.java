package unsw.dungeon.back;

/**
 * Entities that trigger an action when they are "pushed". A push occurs when a
 * Player attempts to move into a Cell, before the player tries to actually take
 * a step in that direction.
 */
public interface Pushable extends Entity {
	/**
	 * Push this entity in a particular direction.
	 * @param p player who's pushing us
	 * @param d direction they're pushing us in
	 */
	void push(Player p, Direction d);
}
