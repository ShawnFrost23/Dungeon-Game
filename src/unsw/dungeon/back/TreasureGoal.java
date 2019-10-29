package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Event;

import unsw.dungeon.back.event.TreasureEvent;

public class TreasureGoal implements Goal, Observer{
	private int numOfTreasureCollected;
	Game game;
	
	@Override
	public boolean isSatisfied() {
		return this.numOfTreausreCollected == 0;
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof TreasurePickedUpEvent) {
			this.numOfTreasure -= 1;
	}
	
}
