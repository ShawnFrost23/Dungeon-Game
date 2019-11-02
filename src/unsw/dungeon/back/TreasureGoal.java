package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Event;

import unsw.dungeon.back.event.TreasureCollectedEvent;

/**
 * A goal that is satisfied so long as all {@link Treasure} has been collected.
 */
public class TreasureGoal implements Goal, Observer {
	private int numTreasureRemaining;
	
	public TreasureGoal() {
		this.numTreasureRemaining = 0;
	}
	
	@Override
	public boolean isSatisfied() {
		return this.numTreasureRemaining == 0;
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof TreasureCollectedEvent) {
			this.numTreasureRemaining -= 1;
		}
	}

	@Override
	public void trackEntity(Entity e) {
		if (e instanceof Treasure) {
			((Treasure) e).attachListener(this);
			this.numTreasureRemaining += 1;
		}
	}
}