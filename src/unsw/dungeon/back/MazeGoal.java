package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.ExitEvent;
import unsw.dungeon.back.event.PlayerSteppedOnExit;

public class MazeGoal implements Goal, Observer{

	private int isExit;
	Game game;
	
	@Override
	public boolean isSatisfied() {
		return this.isExit == 1;
	}
	
	
	@Override
	public void trackEntity(Entity e) {
		if (e instanceof Exit) {
			((Exit) e).attachListener(this);

		}
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof PlayerSteppedOnExit) {
			this.isExit = 1;
		}
	}
	
}
