package unsw.dungeon.spoof;

import unsw.dungeon.back.*;
import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.CellEvent;

/**
 * An item that ought to get crushed by Boulders, but have no other interaction
 * with the world.
 */
public class SpoofCrushableItem implements ObserveCell {
	private Cell location;
	
	public void setLocation(Cell location) {
		this.location = location;
	}

	private void onEnter(CellEnteredEvent event) {
		Moveable who = event.getWhoEntered();
		if (who instanceof Boulder) {
			this.location.removeEntity(this);
		}
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
	public void notifyOf(CellEvent event) {
		if (event instanceof CellEnteredEvent) {
			this.onEnter((CellEnteredEvent) event);
		}
	}
}
