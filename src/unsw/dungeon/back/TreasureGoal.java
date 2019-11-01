package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Event;

import unsw.dungeon.back.event.TreasurePickedUpEvent;

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
		if (event instanceof TreasurePickedUpEvent) {
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