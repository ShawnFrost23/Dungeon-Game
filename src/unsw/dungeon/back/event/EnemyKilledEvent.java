package unsw.dungeon.back.event;

import unsw.dungeon.back.Enemy;

public class EnemyKilledEvent implements Event {
	private Enemy whoDied;
	
	public EnemyKilledEvent(Enemy who) {
		this.whoDied = who;
	}
	
	public Enemy getWhoDied() {
		return this.whoDied;
	}

}
