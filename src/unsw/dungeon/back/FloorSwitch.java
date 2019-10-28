package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.CellExitedEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class FloorSwitch implements Entity, Observer {
	private boolean isPressed;

	public FloorSwitch() {
		 // TODO: isn't false if it has a boulder on top of it to begin with.
		this.isPressed = false;
	}
	
	public void press() {
		this.isPressed = true;
	}
	
	public void unpress() {
		this.isPressed = false;
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
		} else if (event instanceof CellExitedEvent) {
			this.onExit((CellExitedEvent) event); 
		}
	}

	private void onEnter(CellEnteredEvent event) {
		if (event.getWhoEntered() instanceof Boulder) {
			this.press();
		}
	}
	
	private void onExit(CellExitedEvent event) {
		if (event.getWhoExited() instanceof Boulder) {
			this.unpress();
		}
	}

	public boolean isPressed() {
		return isPressed;
	}

}
