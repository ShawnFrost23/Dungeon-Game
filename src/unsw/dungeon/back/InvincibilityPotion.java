package unsw.dungeon.back;
import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.Player;

public class InvincibilityPotion implements Entity, Observer {

	@Override
	public int getZ() {
		return 997;
	}

	@Override
	public char getTexture() {
		return '*';
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
			p.take(this);
		}
	}
}
