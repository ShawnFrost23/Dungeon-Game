package unsw.dungeon.back.event;

import unsw.dungeon.back.Enemy;

/**
 * An Event that is fired when an {@link unsw.dungeon.back.Enemy Enemy} is killed.
 */
public class EnemyKilledEvent implements Event {
	private Enemy whoDied;
	
	/**
	 * Construct a new EnemyKilledEvent.
	 * @param who the enemy who was killed
	 */
	public EnemyKilledEvent(Enemy who) {
		this.whoDied = who;
	}
	
	/**
	 * Get who was killed.
	 * @return the enemy who was killed
	 */
	public Enemy getWhoDied() {
		return this.whoDied;
	}

}
