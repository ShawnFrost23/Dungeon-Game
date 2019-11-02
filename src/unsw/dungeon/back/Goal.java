package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;

/**
 * Objects that extend this interface can be provided to
 * {@link Game#createGame(Goal, String...)}. If the Goal at root level becomes
 * satisfied, the game is won.
 */
public interface Goal extends Observer {
	/**
	 * Returns true if this goal is satisfied, false otherwise.
	 * @return true if this goal is satisfied
	 */
	boolean isSatisfied();
	
	/**
	 * This function is called for every Entity that is added to a Board during
	 * its creation. Implementors of this interface should selectively listen to
	 * Entities that produce events relevant to the satisfaction of the goal.
	 * @param e entity to track
	 */
	void trackEntity(Entity e);
}
