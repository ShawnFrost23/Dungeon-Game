package unsw.dungeon.back;

public interface Pushable extends Entity {
	/**
	 * Push this entity in a particular direction.
	 * @param p player who's pushing us
	 * @param d direction they're pushing us in
	 */
	void push(Player p, Direction d);
}
