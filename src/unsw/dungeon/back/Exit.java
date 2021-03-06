package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.CellExitedEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.ExitSteppedOffEvent;
import unsw.dungeon.back.event.ExitSteppedOnEvent;
import unsw.dungeon.back.event.Subject;

public class Exit implements Entity, Observer, Subject {
	private List<Observer> observers;

	/**
	 * Create a new Exit.
	 */
	public Exit() {
		this.observers = new ArrayList<Observer>(); 
	}
	
	@Override
	public int getZ() {
		return 990;
	}

	@Override
	public Texture getTexture() {
		return new Texture('E', "exit.png");
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
	
	public void onExit(CellExitedEvent event) {
		if (event.getWhoExited() instanceof Player) {
			this.notifyAllOf(new ExitSteppedOffEvent());
		}
	}
	
	private void onEnter(CellEnteredEvent event) {
		if (event.getWhoEntered() instanceof Player) {
			this.notifyAllOf(new ExitSteppedOnEvent());
		}
	}
}
