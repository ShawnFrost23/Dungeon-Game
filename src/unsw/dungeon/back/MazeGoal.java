package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PlayerSteppedOffExitEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.PlayerSteppedOnExitEvent;
/**
 * A goal that is satisfied so long as the {@link Player} is standing on top
 * of an {@link Exit}.
 */
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
		if (event instanceof PlayerSteppedOnExitEvent) {
			this.isPlayerStoodOnExit = true;
		} else if (event instanceof PlayerSteppedOffExitEvent) {
			this.isPlayerStoodOnExit = false;
		}
	}
	
}
