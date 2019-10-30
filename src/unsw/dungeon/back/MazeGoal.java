package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Event;

public class MazeGoal implements Goal, Observer{

	private int isExit;
	Game game;
	
	@Override
	public boolean isSatisfied() {
		return this.isExit == 0;
	}
	
	
	@Override
	public void trackEntity(Entity e) {
		if (e instanceof Exit) {
			((Exit) e).attachListener(this);

		}
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof ExitEvent) {
			this.isExit = 0;
		}
	}
	
}
