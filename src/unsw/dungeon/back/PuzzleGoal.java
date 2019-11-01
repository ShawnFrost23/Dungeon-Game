package unsw.dungeon.back;

import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.FloorSwitchPressedEvent;
import unsw.dungeon.back.event.FloorSwitchUnpressedEvent;
import unsw.dungeon.back.event.Observer;

public class PuzzleGoal implements Goal, Observer {
	private int numUnpressedFloorSwitches;

	public PuzzleGoal() {
		this.numUnpressedFloorSwitches = 0;
	}
	
	@Override
	public boolean isSatisfied() {
		return this.numUnpressedFloorSwitches == 0;
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof FloorSwitchPressedEvent) {
			this.numUnpressedFloorSwitches -= 1;
		} else if (event instanceof FloorSwitchUnpressedEvent) {
			this.numUnpressedFloorSwitches += 1;
		}
	}

	@Override
	public void trackEntity(Entity e) {
		if (e instanceof FloorSwitch) {
			((FloorSwitch) e).attachListener(this);
			this.numUnpressedFloorSwitches += 1;
		}
	}
}
