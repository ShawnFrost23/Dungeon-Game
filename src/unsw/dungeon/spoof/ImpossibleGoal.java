package unsw.dungeon.spoof;

import unsw.dungeon.back.Entity;
import unsw.dungeon.back.Goal;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class ImpossibleGoal implements Goal, Observer {
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
