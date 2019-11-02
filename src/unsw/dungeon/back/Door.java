package unsw.dungeon.back;

import unsw.dungeon.back.event.CellPushedEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Door implements Observer, Entity, Collidable {
	private Cell location;
	private int ID;

	public Door(Cell location, int ID) {
		this.location = location;
		this.ID = ID;
	}
	
	private void open() {
		this.location.removeEntity(this);
		this.location.addEntity(new OpenDoor());
	}
	
	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public char getTexture() {
		return '#';
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellPushedEvent) {
			this.onPush((CellPushedEvent) event);
		}
	}

	private void onPush(CellPushedEvent event) {
		Player p = event.getWhoPushed();
		if (p.hasKey(this.ID)) {
			p.consumeHeldKey();
			this.open();
		}
	}
}
