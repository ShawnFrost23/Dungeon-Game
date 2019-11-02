package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PlayerSteppedOffExit;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.PlayerSteppedOnExit;

public class MazeGoal implements Goal, Observer{
	boolean isPlayerStoodOnExit;
	Game game;
	
	public MazeGoal() {
		this.isPlayerStoodOnExit = false;
	}
	
	@Override
	public boolean isSatisfied() {
		return this.isPlayerStoodOnExit;
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
			this.isPlayerStoodOnExit = true;
		} else if (event instanceof PlayerSteppedOffExit) {
			this.isPlayerStoodOnExit = false;
		}
	}
	
}
