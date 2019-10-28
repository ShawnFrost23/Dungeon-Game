import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.CellExitedEvent;
import unsw.dungeon.back.event.Event;

package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

public class Treasure implements Entity, Observer {
	
	private Cell location;
	
	public Treasure(Cell c) {
		 
		// treasure on given cell
		this.location = c;
	}
	
	public void pickUp(Player player) {
		// Add to player list
		player.attachListener(this);
		// Remove from Board cell
		this.location.removeEntity(this);
	}
	
	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public char getTexture() {
		return '_';
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
			this.pickUp(event.getWhoEntered());
		}
	}
	
}
