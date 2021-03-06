package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Sword implements Entity, Observer {
	private Cell location;

	/**
	 * Construct a new Sword instance.
	 * @param location Cell the Sword is located at
	 */
	public Sword(Cell location) {
		this.location = location;
	}

	@Override
	public int getZ() {
		return 250;
	}

	@Override
	public Texture getTexture() {
		return new Texture('S', "greatsword_1_new.png");
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
		if (who instanceof Player) {
			Player p = (Player) who;
			if (!p.isHoldingSword()) {
				p.take(this);
			}
		}
	}
}
