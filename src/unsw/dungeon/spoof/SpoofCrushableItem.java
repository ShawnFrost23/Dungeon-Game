package unsw.dungeon.spoof;

import unsw.dungeon.back.*;

/**
 * An item that ought to get crushed by Boulders, but have no other interaction
 * with the world.
 */
public class SpoofCrushableItem implements ListenForMovement {
	private Cell location;
	
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
	public void onEnter(Moveable m) {
		if (m instanceof Boulder) {
			this.location.removeEntity(this);
		}
	}

	@Override
	public void onExit(Moveable m) {
	}
}
