package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.CellExitedEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.FloorSwitchPressedEvent;
import unsw.dungeon.back.event.FloorSwitchUnpressedEvent;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Subject;

public class FloorSwitch implements Entity, Observer, Subject {
	private List<Observer> observers;

	public FloorSwitch() {
		this.observers = new ArrayList<Observer>();
	}
	
	/**
	 * Generate a {@link FloorSwitchPressedEvent} and notify all observers.
	 */
	public void press() {
		this.notifyAllOf(new FloorSwitchPressedEvent());
	}
	
	/**
	 * Generate a {@link FloorSwitchUnpressedEvent} and notify all observers.
	 */
	public void unpress() {
		this.notifyAllOf(new FloorSwitchUnpressedEvent());
	}
	
	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public Texture getTexture() {
		return new Texture('_', "pressure_plate.png");
	}

	@Override
	public boolean isCollidable() {
		return false;
	}
	
	@Override
	public void attachListener(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void detachListener(Observer observer) {
		this.observers.remove(observer);
		
	}

	@Override
	public void notifyAllOf(Event event) {
		for (Observer observer : this.observers) {
			observer.notifyOf(event);
		}
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
}
