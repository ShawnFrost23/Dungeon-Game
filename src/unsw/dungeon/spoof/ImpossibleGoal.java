package unsw.dungeon.spoof;

import unsw.dungeon.back.Entity;
import unsw.dungeon.back.Goal;
import unsw.dungeon.back.event.Event;

public class ImpossibleGoal implements Goal {
	@Override
	public boolean isSatisfied() {
		return false;
	}

	@Override
	public void trackEntity(Entity e) {
		
	}

	@Override
	public void notifyOf(Event event) {
		
	}
}
