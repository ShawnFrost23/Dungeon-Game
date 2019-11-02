package unsw.dungeon.back;
import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.Player;

public class InvincibilityPotion implements Entity, Observer {
	private Cell location;
	
	public InvincibilityPotion(Cell c) {
		this.location = c;
	}
	
	public void pickUp() {
		this.location.removeEntity(this);
	}
	
	@Override
	public int getZ() {
		return 997;
	}

	@Override
	public char getTexture() {
		return '*';
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellEnteredEvent) {
			this.onEnter((CellEnteredEvent) event);
		} 
		
	}
	private void onEnter(CellEnteredEvent event) {
		if (event.getWhoEntered() instanceof Player) {
			//Calls pickUp
			this.pickUp();
			((Player)event.getWhoEntered()).invincibilityOn();
		}
	}
}
