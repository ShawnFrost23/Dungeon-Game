package unsw.dungeon.back;

import unsw.dungeon.back.event.EnemyKilledEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class EnemiesGoal implements Goal, Observer {
	private int numEnemiesRemaining;
	
	public EnemiesGoal() {
		this.numEnemiesRemaining = 0;
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof EnemyKilledEvent) {
			this.numEnemiesRemaining -= 1;
		}
	}

	@Override
	public boolean isSatisfied() {
		return this.numEnemiesRemaining == 0;
	}

	@Override
	public void trackEntity(Entity e) {
		if (e instanceof Enemy) {
			((Enemy) e).attachListener(this);
			this.numEnemiesRemaining += 1;
		}
	}

}
