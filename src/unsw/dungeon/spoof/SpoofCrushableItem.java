package unsw.dungeon.spoof;

import unsw.dungeon.back.*;
import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

/**
 * An item that ought to get crushed by Boulders, but have no other interaction
 * with the world.
 */
public class SpoofCrushableItem implements Entity, Observer {
	private Cell location;
	
	public SpoofCrushableItem(Cell cell) {
		this.location = cell;
	}

	public void setLocation(Cell location) {
		this.location = location;
	}

	@Override
	public int getZ() {
		return 9999;
	}

	@Override
	public char getTexture() {
		return '?';
	}

	@Override
	public boolean isCollidable() {
		return false;
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellEnteredEvent) {
			this.onEnter((CellEnteredEvent) event);
		}
	}
	
	private void onEnter(CellEnteredEvent event) {
		Moveable who = event.getWhoEntered();
		if (who instanceof Boulder) {
			this.location.removeEntity(this);
		}
	}
}
