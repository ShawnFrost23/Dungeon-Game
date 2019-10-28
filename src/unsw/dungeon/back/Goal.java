package unsw.dungeon.back;

import unsw.dungeon.back.event.Observer;

public interface Goal extends Observer {
	boolean isSatisfied();
	void trackEntity(Entity e);
}
